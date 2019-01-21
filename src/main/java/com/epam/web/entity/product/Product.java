package com.epam.web.entity.product;

import com.epam.web.entity.Entity;
import com.epam.web.entity.enums.ProductCategory;

import java.io.Serializable;
import java.math.BigDecimal;

public class Product extends Entity implements Serializable {

    private static final long serialVersionUID = -7574180000284625959L;

    private String name;
    private BigDecimal cost;
    private int amount;
    private ProductCategory category;
    private String description;
    private byte[] image;

    public Product(Long id,
                   String name,
                   BigDecimal cost,
                   int amount,
                   ProductCategory category,
                   String description,
                   byte[] image) {
        super(id);
        this.name = name;
        this.cost = cost;
        this.amount = amount;
        this.category = category;
        this.description = description;
        this.image = image;
    }

    public Product() {
        super();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        if (!name.equals(product.name)) return false;
        return category == product.category;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + category.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", cost=" + cost +
                ", amount=" + amount +
                ", category=" + category +
                ", description='" + description + '\'' +
                '}';
    }
}
