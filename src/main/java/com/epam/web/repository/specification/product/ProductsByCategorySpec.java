package com.epam.web.repository.specification.product;

import com.epam.web.repository.specification.Specification;

import java.util.Arrays;
import java.util.Formatter;
import java.util.List;

public class ProductsByCategorySpec implements Specification {

    private String categoryName;

    public ProductsByCategorySpec(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public String toSql() {
        return "SELECT id,name,cost,amount,category,description,image" +
                " FROM product WHERE category=?";
    }

    @Override
    public List<Object> getParameters() {
        return Arrays.asList(categoryName);
    }
}
