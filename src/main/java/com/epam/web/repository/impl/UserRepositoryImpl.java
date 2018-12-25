package com.epam.web.repository.impl;

import com.epam.web.entity.Entity;
import com.epam.web.entity.User;
import com.epam.web.repository.UserRepository;
import com.epam.web.repository.connections.ConnectionPool;
import com.epam.web.repository.converter.Converter;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class UserRepositoryImpl extends AbstractRepository<User> implements UserRepository {

    public UserRepositoryImpl(ConnectionPool connectionPool, Converter<User> converter) {
        super(connectionPool, converter);
    }

    @Override
    <T extends Entity> PreparedStatement getReadyPreparedStatement(T object, PreparedStatement pStatement) throws SQLException {
        return null;
    }

    @Override
    List<String> getFields() {
        return null;
    }

    @Override
    String getTable() {
        return null;
    }
}
