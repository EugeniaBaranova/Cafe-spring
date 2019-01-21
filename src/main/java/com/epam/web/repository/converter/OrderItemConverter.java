package com.epam.web.repository.converter;

import com.epam.web.entity.order.OrderItem;
import com.epam.web.repository.exception.RepositoryException;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderItemConverter implements Converter<OrderItem> {
    @Override
    public OrderItem convert(ResultSet resultSet) throws SQLException, RepositoryException {
        return null;
    }
}
