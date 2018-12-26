package com.epam.web.repository.converter;

import com.epam.web.entity.Product;
import com.epam.web.entity.ProductBuilder;
import com.epam.web.entity.enums.ProductCategory;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductConverter implements Converter<Product> {
    @Override
    public Product convert(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("id");
        String name = resultSet.getString("name");
        String imageReference = resultSet.getString("image_reference");
        BigDecimal cost = resultSet.getBigDecimal("cost");
        int amount = resultSet.getInt("amount");
        String category = resultSet.getString("category");
        String description = resultSet.getString("description");

        return new ProductBuilder().setId(id)
                .setName(name)
                .setImageReference(imageReference)
                .setCost(cost)
                .setAmount(amount)
                .setCategory(ProductCategory.valueOf(category))
                .setDescription(description)
                .createProduct();
    }
}
