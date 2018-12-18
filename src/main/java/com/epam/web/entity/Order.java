package com.epam.web.entity;

import com.epam.web.entity.enums.OrderState;
import com.epam.web.entity.enums.PaymentMethod;

import java.math.BigDecimal;

public class Order extends Entity {

    private Long userId;
    // TODO: 11.12.2018
    private User user;
    private String orderDate;
    private String receivingDate;
    private BigDecimal sum;
    private PaymentMethod paymentMethod;
    private OrderState orderState;
    private boolean paid;
    private int mark;
    private String review;

    public Order(Long id,
                 Long userId,
                 String orderDate,
                 String receivingDate,
                 BigDecimal sum,
                 PaymentMethod paymentMethod,
                 OrderState orderState,
                 boolean paid) {
        super(id);
        this.userId = userId;
        this.orderDate = orderDate;
        this.receivingDate = receivingDate;
        this.sum = sum;
        this.paymentMethod = paymentMethod;
        this.orderState = orderState;
        this.paid = paid;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getReceivingDate() {
        return receivingDate;
    }

    public void setReceivingDate(String receivingDate) {
        this.receivingDate = receivingDate;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public OrderState getOrderState() {
        return orderState;
    }

    public void setOrderState(OrderState orderState) {
        this.orderState = orderState;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }
}
