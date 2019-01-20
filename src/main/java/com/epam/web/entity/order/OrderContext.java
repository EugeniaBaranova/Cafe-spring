package com.epam.web.entity.order;

import com.epam.web.entity.enums.PaymentMethod;
import com.epam.web.entity.product.Product;
import com.epam.web.entity.user.User;

import java.util.Date;
import java.util.List;

public class OrderContext {
    private Date receiving;
    private User customer;
    private List<Product> products;
    private PaymentMethod paymentMethod;


    public OrderContext(Date receiving, List<Product> products, PaymentMethod paymentMethod, User user) {
        this.receiving = receiving;
        this.products = products;
        this.paymentMethod = paymentMethod;
        this.customer = user;
    }


    public Date getReceiving() {
        return receiving;
    }

    public void setReceiving(Date receiving) {
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
}