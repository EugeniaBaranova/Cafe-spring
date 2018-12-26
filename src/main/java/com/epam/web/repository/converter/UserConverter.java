package com.epam.web.repository.converter;

import com.epam.web.entity.User;
import com.epam.web.entity.UserBuilder;
import com.epam.web.entity.enums.UserRole;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserConverter implements Converter<User> {

    @Override
    public User convert(ResultSet resultSet) throws SQLException {

        return new UserBuilder().setId(resultSet.getLong("id"))
                .setName(resultSet.getString("name"))
                .setRole(UserRole.valueOf(resultSet.getString("role").toUpperCase()))
                .setBlocked(resultSet.getBoolean("blocked"))
                .setEmail(resultSet.getString("e_mail"))
                .setLogin( resultSet.getString("login"))
                .setPassword(resultSet.getString("password"))
                .setLoyaltyPoints(resultSet.getInt("loyalty_points"))
                .createUser();
    }
}
