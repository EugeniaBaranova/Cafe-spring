package com.epam.web.repository.impl;

import com.epam.web.entity.Entity;
import com.epam.web.repository.Repository;
import com.epam.web.repository.converter.Converter;
import com.epam.web.repository.exception.RepositoryException;
import com.epam.web.repository.specification.Specification;
import com.epam.web.utils.SqlUtils;
import com.epam.web.utils.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public abstract class AbstractRepository<T extends Entity> implements Repository<T> {
    private static final Logger logger = Logger.getLogger(AbstractRepository.class);
    private static final int FIRST_LIST_ELEMENT = 0;
    private RowMapper<T> rowMapper;
    protected JdbcTemplate jdbcTemplate;


    public AbstractRepository(DataSource dataSource, RowMapper<T> rowMapper) {
        this.rowMapper = rowMapper;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private List<T> executeQuery(String query, List<Object> parameters) throws RepositoryException {

        logger.debug("[executeQuery] Start to execute method. Query :" + query);
        try {
            List<T> entities = new ArrayList<>();
            if (parameters != null) {
                entities = jdbcTemplate.query(query, rowMapper, parameters.toArray());
            }
            logger.debug("[executeQuery] Finish to execute method");
            return entities;
        } catch (Exception e) {
            logger.warn("[executeQuery] SQL Exception while execution method");
            throw new RepositoryException(e.getMessage(), e);
        }
    }

    private Optional<T> executeForSingleResult(String query, List<Object> parameters) throws RepositoryException {
        List<T> entities = executeQuery(query, parameters);
        if (!entities.isEmpty()) {
            T entity = entities.get(FIRST_LIST_ELEMENT);
            return Optional.ofNullable(entity);
        }
        return Optional.empty();
    }

    @Override
    public T add(T entity) throws RepositoryException {
        String query = SqlUtils.getInsertStatement(getTable(), getFields());
        logger.debug("[add] Start to execute method. Query :" + query);
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            int affectedRows = jdbcTemplate.update(
                    connection -> {
                        PreparedStatement preparedStatement = connection.prepareStatement(query, new String[]{"id"});
                        return AbstractRepository.this.getReadyPreparedStatement(entity, preparedStatement);
                    },
                    keyHolder);

            if (entity.getId() == null) {
                Number generatedKeys = keyHolder.getKey();
                if (affectedRows != 0 && generatedKeys != null) {
                    entity.setId(generatedKeys.longValue());
                    logger.debug("[add] Generated id :" + entity.getId());
                    return entity;
                }
            }
            return entity;
        } catch (Exception e) {
            logger.warn("[add]Exception while execute query :" + query);
            throw new RepositoryException(e);
        }
    }

    @Override
    public void remove(T entity) throws RepositoryException {
        String query = SqlUtils.getDeleteStatement(getTable());
        try {
            jdbcTemplate.update(query, entity.getId());
        } catch (Exception e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public T update(T entity) throws RepositoryException {
        Long id = entity.getId();
        String query = SqlUtils.getUpdateStatement(getTable(), id, getFields());
        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                return getReadyPreparedStatement(entity, preparedStatement);
            });
            return entity;
        } catch (Exception e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public Optional<T> queryForSingleResult(Specification specification) throws RepositoryException {
        String query = specification.toSql();
        List<Object> parameters = specification.getParameters();
        if (query != null && parameters != null) {
            return executeForSingleResult(query, parameters);
        }
        return Optional.empty();
    }

    @Override
    public List<T> query(Specification specification) throws RepositoryException {
        List<Object> parameters = specification.getParameters();
        String query = specification.toSql();
        if (StringUtils.isNotEmpty(query) && parameters != null) {
            return executeQuery(query, parameters);
        }
        return Collections.emptyList();
    }

    public abstract PreparedStatement getReadyPreparedStatement(T object, PreparedStatement pStatement) throws SQLException;

    public abstract List<String> getFields();

    public abstract String getTable();
}
