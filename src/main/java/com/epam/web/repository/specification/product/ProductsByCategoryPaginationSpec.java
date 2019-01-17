package com.epam.web.repository.specification.product;

import com.epam.web.repository.specification.Specification;

import java.util.Arrays;
import java.util.List;

public class ProductsByCategoryPaginationSpec implements Specification {

    private String productCategory;
    private int limit;
    private int offset;

    public ProductsByCategoryPaginationSpec(String productCategory, int limit, int offset) {
        this.productCategory = productCategory;
        this.limit = limit;
        this.offset = offset;
    }

    @Override
    public String toSql() {
        return "SELECT id,name,cost,amount,category,description,image" +
                " FROM product WHERE category=? LIMIT ? OFFSET ?";
    }

    @Override
    public List<Object> getParameters() {
        return Arrays.asList(productCategory, limit, offset);
    }
}
