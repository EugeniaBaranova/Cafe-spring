package com.epam.web.repository.impl.orderItem;

import com.epam.web.entity.order.OrderItem;
import com.epam.web.repository.OrderItemRepository;
import com.epam.web.repository.converter.Converter;
import com.epam.web.repository.impl.AbstractRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static com.epam.web.repository.converter.Fields.OrderItem.*;

public class OrderItemRepositoryImpl extends AbstractRepository<OrderItem> implements OrderItemRepository {
    private final static String TABLE_NAME = "orderitem";

    public OrderItemRepositoryImpl(Connection connection, Converter<OrderItem> converter) {
        super(connection, converter);
    }

    @Override
    public PreparedStatement getReadyPreparedStatement(OrderItem orderItem, PreparedStatement pStatement) throws SQLException {
        pStatement.setLong(1,orderItem.getMealId());
        pStatement.setLong(2,orderItem.getOrderId());
        pStatement.setInt(3,orderItem.getCount());
        return pStatement;
    }

    @Override
    public List<String> getFields() {
        return Arrays.asList(MEAL_ID, ORDER_ID, COUNT);
    }

    @Override
    public String getTable() {
        return TABLE_NAME;
    }
}
