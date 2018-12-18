package com.epam.web.repository.specification;

import java.util.List;

public class ProductsByCategorySpec implements Specification{
    
    private String productCategory;
    
    public ProductsByCategorySpec(String productCategory) {
        this.productCategory = productCategory;
    }


    @Override
    public String toSql() {
        return null;
    }

    @Override
    public List<String> getParameters() {
        return null;
    }
}
