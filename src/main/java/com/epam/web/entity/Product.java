package com.epam.web.entity;

import com.epam.web.entity.enums.ProductCategory;

import java.math.BigDecimal;

public class Product extends Entity {

    private String name;
    private String imageReference;
    private BigDecimal cost;
    private int amount;
    private ProductCategory category;
    private String description;

    public Product(Long id,
                   String name,
                   String imageReference,
                   BigDecimal cost,
                   int amount,
                   ProductCategory category,
                   String description) {
        super(id);
        this.name = name;
        this.imageReference = imageReference;
        this.cost = cost;
        this.amount = amount;
        this.category = category;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageReference() {
        return imageReference;
    }

    public void setImageReference(String imageReference) {
        this.imageReference = imageReference;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public ProductCategory getCategory() {
        return category;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
