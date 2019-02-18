package com.epam.web.repository.converter;

import com.epam.web.entity.enums.OrderState;
import com.epam.web.entity.enums.PaymentMethod;
import com.epam.web.entity.order.Order;
import com.epam.web.entity.order.OrderItem;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import static com.epam.web.repository.converter.Fields.ID;
import static com.epam.web.repository.converter.Fields.Order.*;

@Component
public class OrderItemRowMapper implements RowMapper<OrderItem> {
    @Nullable
    @Override
    public OrderItem mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return null;
    }
}
