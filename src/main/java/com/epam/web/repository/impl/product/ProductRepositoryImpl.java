package com.epam.web.repository.impl.product;

import com.epam.web.entity.product.Product;
import com.epam.web.repository.ProductRepository;
import com.epam.web.repository.converter.Converter;
import com.epam.web.repository.exception.RepositoryException;
import com.epam.web.repository.impl.AbstractRepository;
import com.epam.web.utils.SqlUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.epam.web.repository.converter.Fields.Product.*;

@Component
public class ProductRepositoryImpl extends AbstractRepository<Product> implements ProductRepository {

    private final static String TABLE_NAME = "product";

    @Autowired
    public ProductRepositoryImpl(@Qualifier("baseConnectionPool")DataSource dataSource,
                                 RowMapper<Product> rowMapper) {
        super(dataSource, rowMapper);
    }

    @Override
    public Product update(Product entity) throws RepositoryException {
        String query = getUpdateSql(entity);
        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                return getReadyPreparedStatement(entity, preparedStatement);
            });
            return entity;
        } catch (Exception e) {
            throw new RepositoryException(e);
        }
    }

    private String getUpdateSql(Product product) {

        if (product.getImage() == null) {

            return SqlUtils.getUpdateStatement(getTable(),
                    product.getId(),
                    Arrays.asList(NAME, COST, AMOUNT, CATEGORY, DESCRIPTION));
        }

        return SqlUtils.getUpdateStatement(getTable(), product.getId(), getFields());
    }

    @Override
    public PreparedStatement getReadyPreparedStatement(Product newProduct, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, newProduct.getName());
        preparedStatement.setBigDecimal(2, newProduct.getCost());
        preparedStatement.setInt(3, newProduct.getAmount());
        preparedStatement.setString(4, newProduct.getCategory().name());
        preparedStatement.setString(5, newProduct.getDescription());
        if (newProduct.getImage() != null) {
            preparedStatement.setBinaryStream(6, new ByteArrayInputStream(newProduct.getImage()));
        }
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
