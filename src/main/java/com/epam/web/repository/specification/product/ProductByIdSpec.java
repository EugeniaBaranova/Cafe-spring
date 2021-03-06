package com.epam.web.repository.specification.product;

import com.epam.web.repository.specification.Specification;

import java.util.Arrays;
import java.util.List;

public class ProductByIdSpec implements Specification {

    private Long id;

    public ProductByIdSpec(Long id) {
        this.id = id;
    }

    @Override
    public String toSql() {
        return "SELECT id,name,cost,amount,category,description,image" +
                " FROM product WHERE id=?";
    }

    @Override
    public List<Object> getParameters() {
        return Arrays.asList(id);
    }
}
