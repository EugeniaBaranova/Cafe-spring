package com.epam.web.repository.specification;

import java.util.Arrays;
import java.util.List;

public class ProductByIdSpec implements Specification {

    private Long id;

    public ProductByIdSpec(Long id) {
        this.id = id;
    }

    @Override
    public String toSql() {
        return "SELECT id,name,image_reference,cost,amount,category,description" +
                " FROM product WHERE id=?";
    }

    @Override
    public List<Object> getParameters() {
        return Arrays.asList(id);
    }
}
