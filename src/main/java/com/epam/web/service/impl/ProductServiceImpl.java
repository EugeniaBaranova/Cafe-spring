package com.epam.web.service.impl;

import com.epam.web.entity.Product;
import com.epam.web.entity.ProductBuilder;
import com.epam.web.entity.enums.ProductCategory;
import com.epam.web.repository.ProductRepository;
import com.epam.web.repository.Repository;
import com.epam.web.repository.exception.RepositoryException;
import com.epam.web.repository.specification.ProductByIdSpec;
import com.epam.web.repository.specification.ProductsByCategorySpec;
import com.epam.web.service.ProductService;
import com.epam.web.service.exception.ServiceException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ProductServiceImpl implements ProductService {

    private Repository<Product> repository;

    public ProductServiceImpl(Repository<Product> repository) {
        this.repository = repository;
    }

    @Override
    public List<Product> findByCategory(String productCategory) throws ServiceException{

        try {
            return repository.query(new ProductsByCategorySpec(productCategory));
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    @Override
    public Optional<Product> findProduct(Long id) throws ServiceException {
        try {
            return repository.queryForSingleResult(new ProductByIdSpec());
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Product addProduct(Product product) throws ServiceException {
        return new ProductBuilder().createProduct();
    }

    @Override
    public void deleteProduct(Long id) throws ServiceException {
        return;
    }

    @Override
    public Product editProduct(Long id, Product product) throws ServiceException {
        return new ProductBuilder().createProduct();
    }
}
