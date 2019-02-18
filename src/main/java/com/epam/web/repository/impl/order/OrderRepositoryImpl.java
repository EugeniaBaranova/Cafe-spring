package com.epam.web.repository.impl.order;

import com.epam.web.entity.order.Order;
import com.epam.web.repository.OrderRepository;
import com.epam.web.repository.converter.Converter;
import com.epam.web.repository.impl.AbstractRepository;
import com.epam.web.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static com.epam.web.repository.converter.Fields.Order.*;

@Component
public class OrderRepositoryImpl extends AbstractRepository<Order> implements OrderRepository {

    private final static String TABLE_NAME = "`order`";

    @Autowired
    public OrderRepositoryImpl(@Qualifier("baseConnectionPool") DataSource dataSource,
                               RowMapper<Order> rowMapper) {
        super(dataSource, rowMapper);
    }

    @Override
    public PreparedStatement getReadyPreparedStatement(Order order, PreparedStatement pStatement) throws SQLException {
        pStatement.setLong(1, order.getUserId());
        pStatement.setDate(2, Date.valueOf(order.getOrderDate()));
        pStatement.setDate(3, Date.valueOf(order.getReceivingDate()));
        pStatement.setBigDecimal(4, order.getSum());
        pStatement.setString(5, order.getPaymentMethod().name());
        pStatement.setString(6, order.getOrderState().name());
        pStatement.setBoolean(7, order.isPaid());
        //TODO do I need? private String review = "Empty"; in Order class
        pStatement.setInt(8, order.getMark());
        if(StringUtils.isNotEmpty(order.getReview())){
            pStatement.setString(9, order.getReview());

        }else {
            pStatement.setString(9, StringUtils.empty());
        }
        // TODO: 21.01.2019 Set date
        return pStatement;
    }

    @Override
    public List<String> getFields() {
        return Arrays
                .asList(USER_ID, ORDER_DATE, RECEIVING_DATE, SUM,
                        PAYMENT_METHOD, ORDER_STATE, PAID, MARK, REVIEW);
    }

    @Override
    public String getTable() {
        return TABLE_NAME;
    }
}
