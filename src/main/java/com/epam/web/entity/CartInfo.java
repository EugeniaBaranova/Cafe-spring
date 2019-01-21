package com.epam.web.entity;

import com.epam.web.entity.product.Product;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class CartInfo {
    private Map<Product, Integer> products = new HashMap<>();

    public Map<Product, Integer> getProducts() {
        return products;
    }

    public void setProducts(Map<Product, Integer> products) {
        this.products = products;
    }

    public BigDecimal getSum() {
        BigDecimal itemCost = BigDecimal.ZERO;
        BigDecimal totalCost = BigDecimal.ZERO;
        for (Map.Entry<Product, Integer> entry : products.entrySet()) {
            Product product = entry.getKey();
            Integer count = entry.getValue();
            itemCost = new BigDecimal(String
                    .valueOf(product.getCost()
                            .multiply(new BigDecimal(count))));
            totalCost = totalCost.add(itemCost);
        }
        return totalCost;
    }
}
