package com.epam.web.repository.specification;

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
        return "SELECT id,name,image_reference,cost,amount,category,description" +
                " FROM product WHERE category=?";
    }

    @Override
    public List<Object> getParameters() {
        return Arrays.asList(categoryName);
    }
}
