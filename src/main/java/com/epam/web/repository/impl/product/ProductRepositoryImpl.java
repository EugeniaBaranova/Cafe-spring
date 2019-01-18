package com.epam.web.repository.impl.product;

import com.epam.web.entity.product.Product;
import com.epam.web.repository.ProductRepository;
import com.epam.web.repository.connection.pool.BaseConnectionPool;
import com.epam.web.repository.connection.pool.ConnectionPool;
import com.epam.web.repository.converter.Converter;
import com.epam.web.repository.impl.AbstractRepository;

import java.io.ByteArrayInputStream;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.epam.web.repository.converter.ProductFields.*;

public class ProductRepositoryImpl extends AbstractRepository<Product> implements ProductRepository {

    private final static String TABLE_NAME = "product";

    public ProductRepositoryImpl(ConnectionPool connectionPool, Converter<Product> converter) {
        super(connectionPool, converter);
    }

    @Override
    public PreparedStatement getReadyPreparedStatement(Product newProduct, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, newProduct.getName());
        preparedStatement.setBigDecimal(2, newProduct.getCost());
        preparedStatement.setInt(3, newProduct.getAmount());
        preparedStatement.setString(4, newProduct.getCategory().name());
        preparedStatement.setString(5, newProduct.getDescription());
        preparedStatement.setBinaryStream(6, new ByteArrayInputStream(newProduct.getImage()));
        return preparedStatement;
    }


    @Override
    public List<String> getFields() {
        return new ArrayList<>(
                Arrays.asList(NAME, COST, AMOUNT, CATEGORY, DESCRIPTION, IMAGE));
    }

    @Override
    public String getTable() {
        return TABLE_NAME;
    }
}
