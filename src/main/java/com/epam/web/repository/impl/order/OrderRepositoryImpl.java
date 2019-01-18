package com.epam.web.repository.impl.order;

import com.epam.web.entity.order.Order;
import com.epam.web.repository.OrderRepository;
import com.epam.web.repository.connection.pool.ConnectionPool;
import com.epam.web.repository.converter.Converter;
import com.epam.web.repository.impl.AbstractRepository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class OrderRepositoryImpl extends AbstractRepository<Order> implements OrderRepository {

    private final static String TABLE_NAME = "order";

    public OrderRepositoryImpl(ConnectionPool connectionPool, Converter<Order> converter) {
        super(connectionPool, converter);
    }

    @Override
    public PreparedStatement getReadyPreparedStatement(Order object, PreparedStatement pStatement) throws SQLException {
        return null;
    }

    @Override
    public List<String> getFields() {
        return null;
    }

    @Override
    public String getTable() {
        return TABLE_NAME;
    }
}
