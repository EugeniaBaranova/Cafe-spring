package com.epam.web.repository.impl;

import com.epam.web.entity.Entity;
import com.epam.web.entity.Product;
import com.epam.web.entity.User;
import com.epam.web.repository.UserRepository;
import com.epam.web.repository.connections.ConnectionPool;
import com.epam.web.repository.converter.Converter;
import com.epam.web.repository.exception.RepositoryException;
import com.epam.web.utils.StringUtils;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserRepositoryImpl extends AbstractRepository<User> implements UserRepository {

    public UserRepositoryImpl(ConnectionPool connectionPool, Converter<User> converter) {
        super(connectionPool, converter);
    }

    @Override
     PreparedStatement getReadyPreparedStatement(User user, PreparedStatement preparedStatement) throws SQLException {
        String name = user.getName();
        if (name != null) {
            preparedStatement.setString(1, name);
        }

        String email = user.getEmail();
        if (email != null) {
            preparedStatement.setString(2, email);
        }
        String login = user.getLogin();
        if (StringUtils.isNotEmpty(login)) {
            preparedStatement.setString(3, login);
        }
        String password = user.getPassword();
        if (StringUtils.isNotEmpty(password)) {
            preparedStatement.setString(4, password);
        }

        preparedStatement.setInt(5, user.getLoyaltyPoints());
        preparedStatement.setBoolean(6, user.isBlocked());
        preparedStatement.setString(7, user.getRole().name());
        return preparedStatement;
    }

    @Override
    List<String> getFields() {
        return new ArrayList<>(Arrays.asList("name", "e_mail", "login", "password","loyalty_points","blocked","role"));
    }

    @Override
    String getTable() {
        return "user";
    }
}


