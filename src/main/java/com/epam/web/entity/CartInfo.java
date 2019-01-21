package com.epam.web.entity;

import com.epam.web.entity.product.Product;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class CartInfo {

    private Map<Product, Integer> products = new HashMap<>();
    private BigDecimal sum;

    public Map<Product, Integer> getProducts() {
        return products;
    }

    public void setProducts(Map<Product, Integer> products) {
        this.products = products;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }
}
