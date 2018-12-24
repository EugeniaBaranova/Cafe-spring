package com.epam.web.repository.specification;

import java.util.Formatter;
import java.util.List;

public class ProductsByCategorySpec implements Specification {




    @Override
    public String toSql() {

        return "SELECT id,name,image_reference,cost,amount,category,description" +
                " FROM product WHERE category=?";
    }

    @Override
    public List<String> getParameters() {
        return null;
    }
}
