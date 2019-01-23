package com.epam.web.entity.order;

import com.epam.web.entity.enums.PaymentMethod;
import com.epam.web.entity.product.Product;
import com.epam.web.entity.user.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class OrderContext {
    private LocalDate receiving;
    private User customer;
    private List<Product> products;
    private PaymentMethod paymentMethod;
    private List<Long> cartProductIds;



    public OrderContext(LocalDate receiving, List<Product> products, PaymentMethod paymentMethod, User user, List<Long> cartProductIds) {
        this.receiving = receiving;
        this.products = products;
        this.paymentMethod = paymentMethod;
        this.customer = user;
        this.cartProductIds = cartProductIds;
    }


    public List<Long> getCartProductIds() {
        return cartProductIds;
    }

    public void setCartProductIds(List<Long> cartProductIds) {
        this.cartProductIds = cartProductIds;
    }

    public LocalDate getReceiving() {
        return receiving;
    }

    public void setReceiving(LocalDate receiving) {
        this.receiving = receiving;
    }

    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    @Override
    public String toString() {
        return "OrderContext{" +
                "receiving=" + receiving +
                ", customer=" + customer +
                ", products=" + products +
                ", paymentMethod=" + paymentMethod +
                '}';
    }
}
