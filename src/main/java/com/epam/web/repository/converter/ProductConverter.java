package com.epam.web.repository.converter;

import com.epam.web.entity.product.Product;
import com.epam.web.entity.product.ProductBuilder;
import com.epam.web.entity.enums.ProductCategory;
import com.epam.web.repository.exception.RepositoryException;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import static com.epam.web.repository.converter.ProductFields.*;

public class ProductConverter implements Converter<Product> {

    @Override
    public Product convert(ResultSet resultSet) throws SQLException, RepositoryException {
        try{
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            Product product = new Product();
            if (columnCount != 0) {
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnName(i);
                    switch (columnName) {
                        case ID:
                            product.setId(resultSet.getLong(columnName));
                            break;
                        case NAME:
                            product.setName(resultSet.getString(columnName));
                            break;
                        case COST:
                            product.setCost(resultSet.getBigDecimal(columnName));
                            break;
                        case AMOUNT:
                            product.setAmount(resultSet.getInt(columnName));
                            break;
                        case CATEGORY:
                            product.setCategory(ProductCategory.valueOf(resultSet.getString(columnName)));
                            break;
                        case DESCRIPTION:
                            product.setDescription(resultSet.getString(columnName));
                            break;
                        case IMAGE:
                            product.setImage(IOUtils.toByteArray(resultSet.getBinaryStream(columnName)));
                        default:
                            return product;
                    }
                }
            }
            return product;
        } catch (IOException e) {
            //todo better way
            throw new RepositoryException(e.getMessage(), e);
        }
    }
}
