package com.epam.web.service.impl;

import com.epam.web.entity.Product;
import com.epam.web.entity.ProductBuilder;
import com.epam.web.entity.enums.ProductCategory;
import com.epam.web.repository.ProductRepository;
import com.epam.web.repository.factory.RepositoryFactory;
import com.epam.web.repository.specification.ProductByIdSpec;
import com.epam.web.repository.specification.ProductsByCategorySpec;
import com.epam.web.service.ProductService;
import com.epam.web.service.exception.ServiceException;
import com.epam.web.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Formatter;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.ReentrantLock;

public class ProductServiceImpl implements ProductService {
    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class.getName());
    private RepositoryFactory factory = RepositoryFactory.getInstance();
    private ProductRepository productRepository = factory.getRepository(ProductRepository.class);
    private ReentrantLock reentrantLock = new ReentrantLock();


    @Override
    public List<Product> findByCategory(String productCategory) throws ServiceException {
        try {
            if (isAvailableCategory(productCategory)) {
                return getProductRepository()
                        .query(new ProductsByCategorySpec());
            }
        } catch (Exception e) {
            String errorMessage = getErrorFormatter()
                    .format("[findByCategory] Exception while execution method.Method parameter:'%s'")
                    .toString();
            throw new ServiceException(errorMessage, e);
        }
        return Collections.emptyList();
    }


    @Override
    public Optional<Product> findProduct(Long id) throws ServiceException {
        try {
            return getProductRepository().queryForSingleResult(new ProductByIdSpec());
        } catch (Exception e) {
            String errorMessage =
                    getErrorFormatter()
                            .format("[findProduct] Error find Product with id:'%s'", id)
                            .toString();
            throw new ServiceException(errorMessage, e);
        }
    }

    @Override
    public Product addProduct(Product product) throws ServiceException {

        try {
            if (product != null) {
                getLocker().lock();
                logger.debug("[addProduct] Start to execute method. Product to save:{}", product);
                Product savedProduct = getProductRepository().add(product);
                logger.debug("[addProduct] Finish to execute method. Saved product:{}", savedProduct);
            }
        } catch (Exception e) {
            String errorMessage =
                    getErrorFormatter()
                            .format("[addProduct] Error while saving product. Product to save: %s", product)
                            .toString();
            throw new ServiceException(errorMessage, e);
        } finally {
            getLocker().unlock();
        }

        return new ProductBuilder().createProduct();
    }

    @Override
    public void deleteProduct(Long id) throws ServiceException {

        try {
            getLocker().lock();
            logger.debug("[deleteProduct] Start to remove product by id:{}", id);
            Product product = new ProductBuilder()
                    .setId(id)
                    .createProduct();
            getProductRepository().remove(product);
            logger.debug("[deleteProduct] Finish to remove product by id:{}", id);
        } catch (Exception e) {
            String errorMessage =
                    getErrorFormatter()
                            .format("[deleteProduct] Error while removing product. Id: %s", id)
                            .toString();
            throw new ServiceException(errorMessage, e);
        } finally {
            getLocker().unlock();
        }
    }

    @Override
    public Product editProduct(Long id, Product product) throws ServiceException {
        try {
            if (product != null) {
                if (product.getId() != null) {
                    getLocker().lock();
                    logger.debug("[editProduct] Start to update product. product info:{}", product);
                    Product updatedProduct = getProductRepository().update(product);
                    logger.debug("[editProduct] Finish to update product. product info:{}", product);
                    return updatedProduct;
                }
            }
            return null;
        } catch (Exception e) {
            String errorMessage =
                    getErrorFormatter()
                            .format("[editProduct] Error while updating product. product: %s", product)
                            .toString();
            throw new ServiceException(errorMessage, e);
        } finally {
            getLocker().unlock();
        }
    }

    private boolean isAvailableCategory(String categoryName) {
        if (StringUtils.isNotEmpty(categoryName)) {
            for (ProductCategory category : ProductCategory.values()) {
                if (category.name().equals(categoryName)) {
                    return true;
                }
            }
        }
        return false;
    }

    private ReentrantLock getLocker() {
        return reentrantLock;
    }

    private Formatter getErrorFormatter() {
        //using new instance every call of the method cause java.util.Formatter is not thread safe class.
        return new Formatter();
    }

    private ProductRepository getProductRepository() {
        return productRepository;
    }
}
