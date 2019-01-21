package com.epam.web.repository.converter;

import com.epam.web.entity.order.Order;
import com.epam.web.repository.exception.RepositoryException;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderConverter implements Converter<Order> {
    @Override
    public Order convert(ResultSet resultSet) throws SQLException, RepositoryException {
        return null;
    }
}
