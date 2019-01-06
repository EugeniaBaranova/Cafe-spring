package com.epam.web.repository.impl;

import com.epam.web.entity.Entity;
import com.epam.web.repository.Repository;
import com.epam.web.repository.connections.ConnectionPool;
import com.epam.web.repository.converter.Converter;
import com.epam.web.repository.exception.ConnectionPoolException;
import com.epam.web.repository.exception.RepositoryException;
import com.epam.web.repository.specification.Specification;
import com.epam.web.utils.SqlUtils;
import org.apache.log4j.Logger;

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
    private ConnectionPool connectionPool;
    private Converter<T> converter;


    public AbstractRepository(ConnectionPool connectionPool, Converter<T> converter) {
        this.connectionPool = connectionPool;
        this.converter = converter;
    }

    private List<T> executeQuery(String query, List<Object> parameters) throws RepositoryException {
        try (Connection connection = getConnectionPool().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {
            if (parameters != null) {
                int parameterPosition = 1;
                for (Object parameter : parameters) {
                    preparedStatement.setObject(parameterPosition, parameter);
                    parameterPosition++;
                }
            }
            ResultSet resultSet = preparedStatement.executeQuery();
            List<T> entities = new ArrayList<>();
            while (resultSet.next()) {
                T entity = getConverter().convert(resultSet);
                entities.add(entity);
            }
            return entities;
        } catch (SQLException e) {
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

        try (Connection connection = getConnectionPool().getConnection();
             PreparedStatement pStatement = connection.prepareStatement(
                     SqlUtils.getInsertStatement(getTable(), getFields()),
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            PreparedStatement readyPreparedStatement = getReadyPreparedStatement(entity, pStatement);
            int affectedRows = readyPreparedStatement.executeUpdate();
            if (entity.getId() == null) {
                if (affectedRows != 0) {
                    try (ResultSet generatedKeys = readyPreparedStatement.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            entity.setId(generatedKeys.getLong(1));
                            return entity;
                        }
                    }
                }
            }
            return entity;
        }catch(Exception e){
            throw new RepositoryException(e);
        }
    }

    @Override
    public void remove(T object) throws RepositoryException {
        try(Connection connection = getConnectionPool().getConnection();
        PreparedStatement pStatement = connection.prepareStatement(SqlUtils.getDeleteStatement(getTable()))){
            pStatement.setLong(1,object.getId());
            int remove = pStatement.executeUpdate();
        }catch (Exception e){
            throw new RepositoryException(e);
        }
    }

    @Override
    public T update(T entity) throws RepositoryException {
        try {
            /*return saveOrUpdate(entity);*/
            return null;
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
        if (query != null && parameters != null) {
            return executeQuery(query, parameters);
        }
        return Collections.emptyList();
    }

    public abstract PreparedStatement getReadyPreparedStatement(T object, PreparedStatement pStatement) throws SQLException;

    public abstract List<String> getFields();

    public abstract String getTable();

    private ConnectionPool getConnectionPool() {
        return connectionPool;
    }
    private Converter<T> getConverter() {
        return converter;
    }
}
