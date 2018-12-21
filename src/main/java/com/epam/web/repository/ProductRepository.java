package com.epam.web.repository;

import com.epam.web.entity.Product;
import com.epam.web.entity.ProductBuilder;
import com.epam.web.entity.enums.ProductCategory;
import com.epam.web.repository.converter.ProductConverter;
import com.epam.web.repository.exception.RepositoryException;
import com.epam.web.repository.specification.Specification;

import java.math.BigDecimal;
import java.util.*;

public class ProductRepository extends AbstractRepository<Product> {

    public ProductRepository() {
        super(new ProductConverter());
    }

    @Override
    public Product add(Product newProduct) throws RepositoryException {
        return null;
    }

    @Override
    public void remove(Product product) throws RepositoryException {
        return;
    }

    @Override
    public Product update(Product updatedProduct) throws RepositoryException {
        try{

        }catch (Exception e){
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
}
