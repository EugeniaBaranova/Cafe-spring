package com.epam.web.repository.specification.order;

import com.epam.web.repository.specification.Specification;

import java.util.Arrays;
import java.util.List;

public class GetAllOrdersSpec implements Specification {

    private Long userId;
    public GetAllOrdersSpec(Long userId) {
        this.userId = userId;
    }
    @Override
    public String toSql() {
        return "SELECT order_date," +
                " receiving_date," +
                " sum,payment_method," +
                " order_state," +
                " paid," +
                " mark," +
                " review FROM `order` WHERE user_id=?";
    }

    @Override
    public List<Object> getParameters() {
        return Arrays.asList(userId);
    }
}
