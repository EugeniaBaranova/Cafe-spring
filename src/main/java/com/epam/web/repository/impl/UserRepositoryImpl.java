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
    <T extends Entity> PreparedStatement getReadyPreparedStatement(T object, PreparedStatement pStatement) throws SQLException {
        User user = (User) object;

        String name = user.getName();
        if (name != null) {
            pStatement.setString(1, name);
        }

        String email = user.getEmail();
        if (email != null) {
            pStatement.setString(2, email);
        }
        String login = user.getLogin();
        if (StringUtils.isNotEmpty(login)) {
            pStatement.setString(3, login);
        }
        String password = user.getPassword();
        if (StringUtils.isNotEmpty(password)) {
            pStatement.setString(4, password);
        }

        pStatement.setInt(5, user.getLoyaltyPoints());
        pStatement.setBoolean(6, user.isBlocked());
        pStatement.setString(7, user.getRole().name());
        return pStatement;
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


