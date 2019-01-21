package com.epam.web.repository.impl.order;

import com.epam.web.entity.order.Order;
import com.epam.web.repository.OrderRepository;
import com.epam.web.repository.converter.Converter;
import com.epam.web.repository.impl.AbstractRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static com.epam.web.repository.converter.Fields.Order.*;

public class OrderRepositoryImpl extends AbstractRepository<Order> implements OrderRepository {

    private final static String TABLE_NAME = "order";

    public OrderRepositoryImpl(Connection connection, Converter<Order> converter) {
        super(connection, converter);
    }

    @Override
    public PreparedStatement getReadyPreparedStatement(Order order, PreparedStatement pStatement) throws SQLException {

        pStatement.setLong(1, order.getUserId());

        // TODO: 21.01.2019 Set date

        return null;
    }

    @Override
    public List<String> getFields() {
        return Arrays
                .asList(USER_ID, ORDER_DATE, RECEIVING_DATE, SUM
                        , PAYMENT_METHOD, ORDER_STATE, PAID, MARK, REVIEW);
    }

    @Override
    public String getTable() {
        return TABLE_NAME;
    }
}
