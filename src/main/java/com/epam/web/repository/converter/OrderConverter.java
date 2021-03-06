package com.epam.web.repository.converter;

import com.epam.web.entity.enums.OrderState;
import com.epam.web.entity.enums.PaymentMethod;
import com.epam.web.entity.order.Order;
import com.epam.web.repository.exception.RepositoryException;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static com.epam.web.repository.converter.Fields.ID;
import static com.epam.web.repository.converter.Fields.Order.*;

public class OrderConverter implements Converter<Order> {

    @Override
    public Order convert(ResultSet resultSet) throws SQLException, RepositoryException {
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();
        Order order = new Order();
        if (columnCount != 0) {
            for (int i = 1; i <= columnCount; i++) {
                String columnName = metaData.getColumnName(i);
                switch (columnName) {
                    case ID:
                        order.setId(resultSet.getLong(columnName));
                        break;
                    case USER_ID:
                        order.setUserId(resultSet.getLong(columnName));
                        break;
                    case ORDER_DATE:
                        order.setOrderDate(resultSet.getDate(columnName)
                                .toLocalDate());
                        break;
                    case RECEIVING_DATE:
                        order.setReceivingDate(resultSet.getDate(columnName)
                                .toLocalDate());
                        break;
                    case SUM:
                        order.setSum(BigDecimal.valueOf(Double.valueOf(resultSet.getString(columnName))));
                        break;
                    case PAYMENT_METHOD:
                        order.setPaymentMethod(PaymentMethod.valueOf(resultSet.getString(columnName)));
                        break;
                    case ORDER_STATE:
                        order.setOrderState(OrderState.valueOf(resultSet.getString(columnName)));
                        break;
                    case PAID:
                        order.setPaid(resultSet.getBoolean(columnName));
                        break;
                    case MARK:
                        order.setMark(resultSet.getInt(columnName));
                        break;
                    case REVIEW:
                        order.setReview(resultSet.getString(columnName));
                        break;
                    default:
                        return order;
                }
            }
        }
        return order;
    }
}
