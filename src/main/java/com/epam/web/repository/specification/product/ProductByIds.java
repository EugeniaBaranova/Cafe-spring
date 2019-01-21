package com.epam.web.repository.specification.product;

import com.epam.web.repository.specification.Specification;
import com.epam.web.utils.StringUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ProductByIds implements Specification {

    private Set<Long> productsId = new HashSet<>();

    public ProductByIds(Set<Long> ids) {
        if (ids != null) {
            this.productsId = ids;
        }
    }

    @Override
    public String toSql() {

        String sqlValue = sqlProductIds();
        if(StringUtils.isEmpty(sqlValue)){
            return StringUtils.empty();
        }
        return "SELECT  id,name,cost,amount,category,description,image " +
                "FROM " +
                "product " +
                "WHERE " +
                "id " +
                "IN " +
                "(" +
                sqlValue +
                ");";
    }

    @Override
    public List<Object> getParameters() {
        return new ArrayList<>(productsId);
    }

    private String sqlProductIds() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < productsId.size(); i++) {
            sb.append("?");
            sb.append(",");
        }
        if (StringUtils.isNotEmpty(sb.toString())) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return StringUtils.empty();

    }
}
