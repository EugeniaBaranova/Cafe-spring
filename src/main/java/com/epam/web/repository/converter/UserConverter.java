package com.epam.web.repository.converter;

import com.epam.web.entity.User;
import com.epam.web.entity.UserBuilder;
import com.epam.web.entity.enums.UserRole;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserConverter implements Converter<User> {

    @Override
    public User convert(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("id");
        String name = resultSet.getString("name");
        String role = resultSet.getString("role")
                .toUpperCase();
        boolean blocked = resultSet.getBoolean("blocked");
        String email = resultSet.getString("e_mail");
        String login = resultSet.getString("login");
        String password = resultSet.getString("password");
        int loyaltyPoints = resultSet.getInt("loyalty_points");

        return new UserBuilder().setId(id)
                .setName(name)
                .setRole(UserRole.valueOf(role))
                .setBlocked(blocked)
                .setEmail(email)
                .setLogin(login)
                .setPassword(password)
                .setLoyaltyPoints(loyaltyPoints)
                .createUser();
    }
}
