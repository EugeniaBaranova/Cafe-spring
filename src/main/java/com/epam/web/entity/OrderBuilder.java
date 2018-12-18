package com.epam.web.entity;

import com.epam.web.entity.enums.OrderState;
import com.epam.web.entity.enums.PaymentMethod;

import java.math.BigDecimal;

public class OrderBuilder {
    private Long id;
    private Long userId;
    private String orderDate;
    private String receivingDate;
    private BigDecimal sum;
    private PaymentMethod paymentMethod;
    private OrderState orderState;
    private boolean paid;

    public OrderBuilder setId(Long id) {
        this.id = id;
        return this;
    }

    public OrderBuilder setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public OrderBuilder setOrderDate(String orderDate) {
        this.orderDate = orderDate;
        return this;
    }

    public OrderBuilder setReceivingDate(String receivingDate) {
        this.receivingDate = receivingDate;
        return this;
    }

    public OrderBuilder setSum(BigDecimal sum) {
        this.sum = sum;
        return this;
    }

    public OrderBuilder setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
        return this;
    }

    public OrderBuilder setOrderState(OrderState orderState) {
        this.orderState = orderState;
        return this;
    }

    public OrderBuilder setPaid(boolean paid) {
        this.paid = paid;
        return this;
    }

    public Order createOrder() {
        return new Order(id, userId, orderDate, receivingDate, sum, paymentMethod, orderState, paid);
    }
}