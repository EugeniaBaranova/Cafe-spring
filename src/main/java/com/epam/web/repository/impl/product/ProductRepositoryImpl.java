package com.epam.web.repository.impl.product;

import com.epam.web.entity.product.Product;
import com.epam.web.repository.ProductRepository;
import com.epam.web.repository.connections.ConnectionPool;
import com.epam.web.repository.converter.Converter;
import com.epam.web.repository.impl.AbstractRepository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProductRepositoryImpl extends AbstractRepository<Product> implements ProductRepository {


    public ProductRepositoryImpl(ConnectionPool connectionPool, Converter<Product> converter) {
        super(connectionPool, converter);
    }

    @Override
    public PreparedStatement getReadyPreparedStatement(Product newProduct, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, newProduct.getName());
        preparedStatement.setString(2, newProduct.getImageReference());
        preparedStatement.setBigDecimal(3, newProduct.getCost());
        preparedStatement.setInt(4, newProduct.getAmount());
        preparedStatement.setString(5, newProduct.getCategory().name());
        preparedStatement.setString(6, newProduct.getDescription());
        return preparedStatement;
    }


    @Override
    public List<String> getFields() {
        return new ArrayList<>(
                Arrays.asList("id", "name", "image_reference", "cost", "amount", "category", "description"));
    }

    @Override
    public String getTable() {
        return "product";
    }
}
