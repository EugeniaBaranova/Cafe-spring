package com.epam.web.service.impl.product;

import com.epam.web.entity.SavingResult;
import com.epam.web.entity.enums.ProductCategory;
import com.epam.web.entity.product.Product;
import com.epam.web.entity.product.ProductBuilder;
import com.epam.web.entity.validation.ValidationResult;
import com.epam.web.repository.ProductRepository;
import com.epam.web.repository.Repository;
import com.epam.web.repository.RepositoryFactory;
import com.epam.web.repository.connection.RepositorySource;
import com.epam.web.repository.impl.AbstractRepository;
import com.epam.web.repository.specification.product.*;
import com.epam.web.service.ProductService;
import com.epam.web.service.exception.ServiceException;
import com.epam.web.service.impl.BaseServiceImpl;
import com.epam.web.service.validation.Validator;
import com.epam.web.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.util.*;

public class ProductServiceImpl extends BaseServiceImpl<Product> implements ProductService {
    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(ProductServiceImpl.class);

    public ProductServiceImpl(RepositoryFactory repositoryFactory, RepositorySource repositorySource, Validator<Product> validator) {
        super(repositoryFactory, repositorySource, validator);
    }


    @Override
    public List<Product> findByCategory(String productCategory, int currentPage, int countOnPage) throws ServiceException {
        try (Connection connection = getRepositorySource().getConnection()) {
            if (isAvailableCategory(productCategory)) {
                int offset = (currentPage * countOnPage) - countOnPage;
                return getRepository(connection)
                        .query(
                                new ProductsByCategoryPaginationSpec(
                                        productCategory,
                                        countOnPage,
                                        offset));
            }
        } catch (Exception e) {
            logger.warn("[findByCategory] Exception while execution method");
            String errorMessage = getErrorFormatter()
                    .format("[findByCategory] Exception while execution method. Method parameter:'%s', '%s'", productCategory, currentPage)
                    .toString();
            throw new ServiceException(errorMessage, e);
        }
        return Collections.emptyList();
    }

    @Override
    public List<Product> findByCategory(String productCategory) throws ServiceException {
        try (Connection connection = getRepositorySource().getConnection()) {
            if (isAvailableCategory(productCategory)) {
                return getRepository(connection)
                        .query(new ProductsByCategorySpec(productCategory));
            }
        } catch (Exception e) {
            logger.warn("[findByCategory] Exception while execution method");
            String errorMessage = getErrorFormatter()
                    .format("[findByCategory] Exception while execution method. Method parameter:'%s'", productCategory)
                    .toString();
            throw new ServiceException(errorMessage, e);
        }
        return Collections.emptyList();
    }

    @Override
    public int amountInCategory(String productCategory) throws ServiceException {
        try (Connection connection = getRepositorySource().getConnection()) {
            if (isAvailableCategory(productCategory)) {
                List<Product> products = getRepository(connection)
                        .query(new ProductAmountInCategorySpec(productCategory));
                if (products != null) {
                    return products.size();
                }
            }
        } catch (Exception e) {
            logger.warn("[amountInCategory] Exception while execution method");
            String errorMessage = getErrorFormatter()
                    .format("[amountInCategory] Exception while execution method. Method parameter:'%s'", productCategory)
                    .toString();
            throw new ServiceException(errorMessage, e);
        }
        return 0;
    }

    @Override
    public Optional<Product> findProduct(Long id) throws ServiceException {
        try (Connection connection = getRepositorySource().getConnection()) {
            return getRepository(connection)
                    .queryForSingleResult(new ProductByIdSpec(id));
        } catch (Exception e) {
            logger.warn("[findProduct] Exception while execution method. Product : " + id);
            String errorMessage =
                    getErrorFormatter()
                            .format("[findProduct] Error find Product with id:'%s'", id)
                            .toString();
            throw new ServiceException(errorMessage, e);
        }
    }

    @Override
    public SavingResult<Product> addProduct(Product product) throws ServiceException {

        try (Connection connection = getRepositorySource().getConnection()) {
            logger.debug("[addProduct] Start to execute method. Product to save: " + product);

            ValidationResult validResult = getValidator().validate(product);
            logger.debug("[addProduct] Got validation error: " + product);
            if (validResult.hasError()) {

                return new SavingResult<>(validResult.getErrors());
            }

            Product savedProduct = getRepository(connection).add(product);
            logger.debug("[addProduct] Finish to execute method. Saved product:" + savedProduct);
            return new SavingResult<>(savedProduct);
        } catch (Exception e) {
            logger.warn("[addProduct] Exception while execution method.");
            String errorMessage =
                    getErrorFormatter()
                            .format("[addProduct] Error while saving product. Product to save: %s", product)
                            .toString();
            throw new ServiceException(errorMessage, e);
        }
    }

    @Override
    public void deleteProduct(Long id) throws ServiceException {
        try (Connection connection = getRepositorySource().getConnection()) {
            logger.debug("[deleteProduct] Start to remove product by id:{}" + id);
            Product product = new ProductBuilder()
                    .setId(id)
                    .build();
            getRepository(connection)
                    .remove(product);
            logger.debug("[deleteProduct] Finish to remove product by id:" + id);
        } catch (Exception e) {
            logger.warn("[deleteProduct] Exception while execution method. Product id:" + id);
            String errorMessage =
                    getErrorFormatter()
                            .format("[deleteProduct] Error while removing product. Id: %s", id)
                            .toString();
            throw new ServiceException(errorMessage, e);
        }
    }

    @Override
    public SavingResult<Product> editProduct(Long id, Product product) throws ServiceException {
        try (Connection connection = getRepositorySource().getConnection()) {
            logger.debug("[editProduct] Start to update product. product info:" + product);
            ValidationResult validResult = getValidator().validate(product);
            if (validResult.hasError()) {
                logger.debug("[editProduct] Invalid product. Errors:" + validResult.getErrors());
                return new SavingResult<>(validResult.getErrors());
            }

            Product updated = getRepository(connection).update(product);
            logger.debug("[editProduct] Finish to update product. product info:"+ product);
            return new SavingResult<>(updated);
        } catch (Exception e) {
            logger.warn("[editProduct] Exception while execution method. Product id:" + id);
            String errorMessage =
                    getErrorFormatter()
                            .format("[editProduct] Error while updating product. product: %s", product)
                            .toString();
            throw new ServiceException(errorMessage, e);
        }
    }

    @Override
    public List<Product> findAllByIdWithoutImage(Set<Long> productIds) {
        try (Connection connection = getRepositorySource().getConnection()) {
            return getRepository(connection)
                    .query(new ProductsWithoutImageByIdsSpec(productIds));
        } catch (Exception e) {
            logger.warn("[findAllByIdWithoutImage] Exception while execution method.");
        }
        return Collections.emptyList();
    }

    @Override
    public List<Product> findAllById(Set<Long> productIds) throws ServiceException {
        try (Connection connection = getRepositorySource().getConnection()) {
            return getRepository(connection)
                    .query(new ProductByIds(productIds));
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    private boolean isAvailableCategory(String categoryName) {
        if (StringUtils.isNotEmpty(categoryName)) {
            for (ProductCategory category : ProductCategory.values()) {
                if (category.name().equals(categoryName.toUpperCase())) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    protected Repository<Product> getRepository(Connection connection) {
        return getRepositoryFactory()
                .newInstance(
                        ProductRepository.class,
                        connection);
    }


    private Formatter getErrorFormatter() {
        //using new instance every call of the method cause java.util.Formatter is not thread safe class.
        return new Formatter();
    }


}
