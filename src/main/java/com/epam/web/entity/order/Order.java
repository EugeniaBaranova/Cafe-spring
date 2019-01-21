package com.epam.web.entity.order;

import com.epam.web.entity.Entity;
import com.epam.web.entity.enums.OrderState;
import com.epam.web.entity.enums.PaymentMethod;
import com.epam.web.entity.user.User;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Order extends Entity {

    private Long userId;
    private LocalDate orderDate;
    private LocalDate receivingDate;
    private BigDecimal sum;
    private PaymentMethod paymentMethod;
    private OrderState orderState;
    private boolean paid;
    private int mark;
    private String review;


    public Order() {
    }

    public Order(Long id,
                 Long userId,
                 LocalDate orderDate,
                 LocalDate receivingDate,
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

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public LocalDate getReceivingDate() {
        return receivingDate;
    }

    public void setReceivingDate(LocalDate receivingDate) {
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
