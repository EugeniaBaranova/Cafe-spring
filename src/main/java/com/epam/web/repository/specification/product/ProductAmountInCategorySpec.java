package com.epam.web.repository.specification.product;

import com.epam.web.repository.specification.Specification;

import java.util.Arrays;
import java.util.List;

public class ProductAmountInCategorySpec implements Specification {

    private String productCategory;

    public ProductAmountInCategorySpec(String productCategory) {
        this.productCategory = productCategory;
    }

    @Override
    public String toSql() {
        return "SELECT id FROM product WHERE category=?";
    }

    @Override
    public List<Object> getParameters() {
        return Arrays.asList(productCategory);
    }
}
