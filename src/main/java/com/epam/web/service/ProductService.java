package com.epam.web.service;

import com.epam.web.entity.product.Product;
import com.epam.web.service.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface ProductService extends Service{

    List<Product> findByCategory(String productCategory) throws ServiceException;

    Optional<Product> findProduct(Long id) throws ServiceException;

    Product addProduct(Product product) throws ServiceException;

    void deleteProduct(Long id) throws ServiceException;

    Product editProduct(Long id, Product product) throws ServiceException;
}
