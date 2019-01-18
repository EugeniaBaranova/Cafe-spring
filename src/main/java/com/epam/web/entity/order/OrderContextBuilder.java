package com.epam.web.entity.order;

import com.epam.web.entity.enums.PaymentMethod;
import com.epam.web.entity.product.Product;
import com.epam.web.entity.user.User;

import java.util.Date;
import java.util.List;

public class OrderContextBuilder {
    private Date recieving;
    private List<Product> products;
    private PaymentMethod paymentMethod;
    private User customr;

    public OrderContextBuilder setRecieving(Date recieving) {
        this.recieving = recieving;
        return this;
    }

    public OrderContextBuilder setCustomer(User customer) {
        this.customr = customer;
        return this;
    }

    public OrderContextBuilder setProducts(List<Product> products) {
        this.products = products;
        return this;
    }

    public OrderContextBuilder setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
        return this;
    }

    public OrderContext createOrderContext() {
        return new OrderContext(recieving, products, paymentMethod, customr);
    }
}