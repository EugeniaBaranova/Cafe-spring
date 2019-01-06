package com.epam.web.repository.converter;


import com.epam.web.entity.user.User;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import static com.epam.web.repository.converter.UserFields.*;

public class UserConverter implements Converter<User> {

    @Override
    public User convert(ResultSet resultSet) throws SQLException {
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();
        User user = new User();
        if (columnCount != 0) {
            for (int i = 1; i <= columnCount; i++) {
                String columnName = metaData.getColumnName(i);
                switch (columnName) {
                    case ID:
                        user.setId(resultSet.getLong(columnName));
                    case NAME:
                        user.setName(resultSet.getString(columnName));
                    case LOGIN:
                        user.setLogin(resultSet.getString(columnName));
                    case PASSWORD:
                        user.setPassword(resultSet.getString(columnName));
                    case EMAIL:
                        user.setEmail(resultSet.getString(columnName));
                    case LOYALTY_POINTS:
                        user.setLoyaltyPoints(resultSet.getInt(columnName));
                    case BLOCKED:
                        user.setBlocked(resultSet.getBoolean(columnName));
                    default:
                        return user;
                }
            }
        }
        return user;
    }

}
