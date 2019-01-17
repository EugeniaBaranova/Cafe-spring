package com.epam.web.entity.product;

import com.epam.web.entity.enums.ProductCategory;

import java.math.BigDecimal;

public class ProductBuilder {

    private Long id;
    private String name;
    private BigDecimal cost;
    private int amount;
    private ProductCategory category;
    private String description;
    private byte[] image;

    public ProductBuilder setId(Long id) {
        this.id = id;
        return this;
    }

    public ProductBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public ProductBuilder setCost(BigDecimal cost) {
        this.cost = cost;
        return this;
    }

    public ProductBuilder setAmount(int amount) {
        this.amount = amount;
        return this;
    }

    public ProductBuilder setCategory(ProductCategory category) {
        this.category = category;
        return this;
    }

    public ProductBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    public ProductBuilder setImage(byte[] image) {
        this.image = image;
        return this;
    }

    public Product createProduct() {
        return new Product(id, name, cost, amount, category, description, image);
    }
}