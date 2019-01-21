package com.epam.web.service;

import com.epam.web.entity.SavingResult;
import com.epam.web.entity.product.Product;
import com.epam.web.service.exception.ServiceException;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ProductService extends Service{

    List<Product> findByCategory(String productCategory, int currentPage, int countOnPage) throws ServiceException;

    List<Product> findByCategory(String productCategory) throws ServiceException;

    int amountInCategory(String productCategory) throws ServiceException;

    Optional<Product> findProduct(Long id) throws ServiceException;

    SavingResult<Product> addProduct(Product product) throws ServiceException;

    void deleteProduct(Long id) throws ServiceException;

    SavingResult<Product> editProduct(Long id, Product product) throws ServiceException;

    List<Product> findAllById(Set<Long> productIds);
}
