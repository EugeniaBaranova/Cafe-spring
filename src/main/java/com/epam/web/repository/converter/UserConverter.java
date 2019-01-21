package com.epam.web.repository.converter;


import com.epam.web.entity.enums.UserRole;
import com.epam.web.entity.user.User;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import static com.epam.web.repository.converter.Fields.User.*;
import static com.epam.web.repository.converter.Fields.ID;

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
                        break;
                    case NAME:
                        user.setName(resultSet.getString(columnName));
                        break;
                    case LOGIN:
                        user.setLogin(resultSet.getString(columnName));
                        break;
                    case PASSWORD:
                        user.setPassword(resultSet.getString(columnName));
                        break;
                    case EMAIL:
                        user.setEmail(resultSet.getString(columnName));
                        break;
                    case LOYALTY_POINTS:
                        user.setLoyaltyPoints(resultSet.getInt(columnName));
                        break;
                    case BLOCKED:
                        user.setBlocked(resultSet.getBoolean(columnName));
                        break;
                    case ROLE:
                        user.setRole(UserRole.valueOf(resultSet.getString(columnName)));
                        break;
                    default:
                        return user;
                }
            }
        }
        return user;
    }

}
