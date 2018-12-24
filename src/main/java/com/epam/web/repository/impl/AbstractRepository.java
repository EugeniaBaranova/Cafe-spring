package com.epam.web.repository.impl;

import com.epam.web.entity.Entity;
import com.epam.web.repository.Repository;
import com.epam.web.repository.connections.ConnectionPool;
import com.epam.web.repository.connections.ConnectionWrapper;
import com.epam.web.repository.converter.Converter;
import com.epam.web.repository.exception.ConnectionPoolException;
import com.epam.web.repository.exception.RepositoryException;
import com.epam.web.repository.specification.Specification;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class AbstractRepository<T extends Entity> implements Repository<T> {

    private static final Logger logger = Logger.getLogger(AbstractRepository.class);

    private static final int FIRST_LIST_ELEMENT = 0;

    private ConnectionPool connectionPool = ConnectionPool.getInstance();

    private Converter<T> converter;

    public AbstractRepository(Converter<T> converter) {
        this.converter = converter;
    }

    private List<T> executeQuery(String query, List<String> parameters) throws RepositoryException {
        try (ConnectionWrapper connectionWrapper = new ConnectionWrapper(connectionPool.getConnection());
             PreparedStatement preparedStatement = connectionWrapper.prepareStatement(query)
        ) {
            if (parameters != null) {
                int parameterPosition = 1;
                for (String parameter: parameters) {
                    preparedStatement.setString(parameterPosition, parameter);
                    parameterPosition++;
                }
            }
            ResultSet resultSet = preparedStatement.executeQuery();

            List<T> entities = new ArrayList<>();
            while (resultSet.next()) {
                T entity = converter.convert(resultSet);
                entities.add(entity);
            }
            return entities;
        } catch (ConnectionPoolException | IOException | SQLException e) {
            logger.error(e.getMessage(), e);
            throw new RepositoryException(e.getMessage(), e);
        }
    }

    private Optional<T> executeForSingleResult(String query, List<String> parameters) throws RepositoryException {
        List<T> entities = executeQuery(query, parameters);
        if (!entities.isEmpty()) {
            T entity = entities.get(FIRST_LIST_ELEMENT);
            return Optional.of(entity);
        }
        return Optional.empty();
    }


    @Override
    public T add(T object) throws RepositoryException {
        return null;
    }

    @Override
    public void remove(T object) throws RepositoryException {
        return;
    }

    @Override
    public T update(T object) throws RepositoryException {
        return null;
    }

    @Override
    public Optional<T> queryForSingleResult(Specification specification) throws RepositoryException {
        //TODO
        String query = specification.toSql();
        List<String> parameters = specification.getParameters();
        if (query != null && parameters != null) {
            return executeForSingleResult(query, parameters);
        }
        return Optional.empty();
    }

    @Override
    public List<T> query(Specification specification) throws RepositoryException {
        //TODO
        List<String> parameters = specification.getParameters();
        String query = specification.toSql();
        if (query != null && parameters != null) {
            return executeQuery(query, parameters);
        }
        return Collections.emptyList();
    }

}
