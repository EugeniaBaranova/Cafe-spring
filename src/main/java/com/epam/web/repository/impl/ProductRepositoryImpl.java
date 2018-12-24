package com.epam.web.repository.impl;

import com.epam.web.entity.Product;
import com.epam.web.entity.ProductBuilder;
import com.epam.web.entity.enums.ProductCategory;
import com.epam.web.repository.ProductRepository;
import com.epam.web.repository.connections.ConnectionPool;
import com.epam.web.repository.converter.Converter;
import com.epam.web.repository.converter.ProductConverter;
import com.epam.web.repository.exception.RepositoryException;
import com.epam.web.repository.factory.EntityConverterFactory;
import com.epam.web.repository.specification.Specification;
import com.epam.web.utils.SqlUtils;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ProductRepositoryImpl extends AbstractRepository<Product> implements ProductRepository {

    private EntityConverterFactory converterFactory = EntityConverterFactory.getInstance();
    private Converter<Product> converter = converterFactory.getConverter(ProductConverter.class);
    private ConnectionPool connectionPool = ConnectionPool.getInstance();


    public ProductRepositoryImpl(Converter<Product> converter) {
        super(converter);
    }


    @Override
    public Product add(Product newProduct) throws RepositoryException {


        try (Connection connection = getConnectionPool().getConnection();
             PreparedStatement pStatement = connection.prepareStatement(SqlUtils.INSERT_PRODUCT_STATEMENT,
                     PreparedStatement.RETURN_GENERATED_KEYS)) {

            PreparedStatement readyStatement = getReadyProductStatement(newProduct, pStatement);
            int affectedRows = readyStatement.executeUpdate();
            if (affectedRows != 0) {
                try (ResultSet generatedKeys = readyStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        newProduct.setId(generatedKeys.getLong("id"));
                        return newProduct;
                    }
                }
            }
            return null;
        } catch (Exception e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public void remove(Product product) throws RepositoryException {
        return;
    }

    @Override
    public Product update(Product updatedProduct) throws RepositoryException {
        try {

        } catch (Exception e) {
            // TODO: 11.12.2018 Logging

        }

        return null;
    }

    @Override
    public Optional<Product> queryForSingleResult(Specification specification) throws RepositoryException {
        super.queryForSingleResult(specification);
        return Optional.empty();
    }

    @Override
    public List<Product> query(Specification specification) throws RepositoryException {
        super.query(specification);
        Product cola = new ProductBuilder()
                .setId(1L)
                .setName("Coca-cola")
                .setCost(BigDecimal.valueOf(1.95))
                .setAmount(12)
                .setCategory(ProductCategory.DRINK)
                .setDescription("Coca-cola drink.")
                .setImageReference("../../img/products/Coca-cola.jpg")
                .createProduct();
        List<Product> products = new ArrayList<>(Arrays.asList(cola, cola, cola, cola, cola, cola, cola, cola, cola));
        return products;
    }

    private PreparedStatement getReadyProductStatement(Product newProduct, PreparedStatement pStatement) throws SQLException {
        pStatement.setString(1, newProduct.getName());
        pStatement.setString(2, newProduct.getImageReference());
        pStatement.setBigDecimal(3, newProduct.getCost());
        pStatement.setInt(4, newProduct.getAmount());
        pStatement.setString(5, newProduct.getCategory().name());
        pStatement.setString(6, newProduct.getDescription());
        return pStatement;
    }



    public Converter<Product> getConverter() {
        return converter;
    }

    public ConnectionPool getConnectionPool() {
        return connectionPool;
    }
}
