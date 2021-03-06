package com.epam.web.service.impl.order;

import com.epam.web.entity.enums.OrderState;
import com.epam.web.entity.order.Order;
import com.epam.web.entity.order.OrderContext;
import com.epam.web.entity.order.OrderItem;
import com.epam.web.entity.product.Product;
import com.epam.web.entity.user.User;
import com.epam.web.repository.*;
import com.epam.web.repository.connection.RepositorySource;
import com.epam.web.repository.exception.RepositoryException;
import com.epam.web.repository.specification.order.GetAllOrdersSpec;
import com.epam.web.service.OrderService;
import com.epam.web.service.exception.ServiceException;
import com.epam.web.service.impl.BaseServiceImpl;
import com.epam.web.service.validation.Validator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class OrderServiceImpl extends BaseServiceImpl<Order> implements OrderService {

    private static final Logger logger = Logger.getLogger(OrderServiceImpl.class);

    private DataSourceTransactionManager dataSourceTransactionManager;

    @Autowired
    public OrderServiceImpl(RepositoryFactory repositoryFactory,
                            Validator<Order> validator,
                            Repository<Order> repository,
                            DataSourceTransactionManager dataSourceTransactionManager) {
        super(repositoryFactory, validator, repository);
        this.dataSourceTransactionManager = dataSourceTransactionManager;
    }


    @Override
    public Order makeOrder(OrderContext orderContext) throws ServiceException {
        logger.debug("[makeOrder] Start to execute method. Order info : " + orderContext);

        User customer = orderContext.getCustomer();
        DataSource dataSource = dataSourceTransactionManager.getDataSource();
        TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
        TransactionStatus transactionStatus = dataSourceTransactionManager.getTransaction(transactionDefinition);
        try {
            logger.debug("[makeOrder] Begin transaction. Customer :" + customer.getId());
            //TODO how to begin?

            ProductRepository productRepository = getRepositoryFactory()
                    .newInstance(ProductRepository.class, dataSource);
            OrderRepository orderRepository = getRepositoryFactory()
                    .newInstance(OrderRepository.class, dataSource);
            OrderItemRepository orderItemRepository = getRepositoryFactory()
                    .newInstance(OrderItemRepository.class, dataSource);
            List<Product> products = orderContext.getProducts();
            if (products != null) {
                Map<Product, Integer> productCountMap
                        = getProductCountMap(products, orderContext.getCartProductIds());
                BigDecimal productCostSum = getProductCostSum(productCountMap);
                Order order = new Order();
                order.setOrderState(OrderState.WAITING);
                order.setPaymentMethod(orderContext.getPaymentMethod());
                order.setOrderDate(LocalDate.now());
                order.setPaymentMethod(orderContext.getPaymentMethod());
                LocalDate dateTime = orderContext.getReceiving();
                if (dateTime != null) {
                    order.setReceivingDate(dateTime);
                }
                order.setSum(productCostSum);
                order.setUserId(customer.getId());
                logger.debug("[makeOrder] Start to to save order. Customer :" + customer.getId() + ", Order : " + order);
                Order savedOrder = orderRepository.add(order);
                logger.debug("[makeOrder] Finish to save order. Customer :" + customer.getId() + ", Saved id: " + savedOrder.getId());

                List<OrderItem> orderItems = createOrderItems(productCountMap, order);
                this.saveOrderItems(orderItems, orderItemRepository);
                logger.debug("[makeOrder] Finish to save orderItems. Customer :" + customer.getId());

                this.updateProductAmounts(productCountMap, productRepository);
                logger.debug("[makeOrder] Finish to update product Amount. Customer :" + customer.getId());

                dataSourceTransactionManager.commit(transactionStatus);
                return savedOrder;
            }
            return null;
        } catch (Exception e) {
            logger.warn("[makeOrder] Exception while execute transaction. Do Roll back. Customer :" + customer.getId());
            dataSourceTransactionManager.rollback(transactionStatus);
            logger.warn("[makeOrder] Roll back done. Customer :" + customer.getId());
            throw new ServiceException("Exception while execution method. Customer :" + customer.getId());
        } finally {
            logger.debug("[makeOrder] Finish to execute method. Close transaction. Customer :" + customer.getId());
            //TODO how to close?
        }
    }

    @Override
    public List<Order> getAllByUserId(Long userId) throws ServiceException {

        try {
            return getMainRepository()
                    .query(new GetAllOrdersSpec(userId));
        } catch (Exception e) {
            logger.warn("[getAllByUserId] Exception while execution service method");
            throw new ServiceException(e);
        }
    }

    private List<OrderItem> createOrderItems(Map<Product, Integer> productCountMap, Order order) {
        List<OrderItem> orderItems = new ArrayList<>();
        if (productCountMap != null) {
            for (Map.Entry<Product, Integer> entry : productCountMap.entrySet()) {
                OrderItem orderItem = new OrderItem();
                orderItem.setOrderId(order.getId());
                orderItem.setMealId(entry
                        .getKey()
                        .getId());
                orderItem.setCount(entry.getValue());
                orderItems.add(orderItem);
            }
        }
        return orderItems;
    }


    private void saveOrderItems(List<OrderItem> orderItems, OrderItemRepository repository) throws RepositoryException {
        for (OrderItem orderItem : orderItems) {
            repository.add(orderItem);
        }
    }

    private void updateProductAmounts(Map<Product, Integer> productCountMap, ProductRepository productRepository) throws RepositoryException {
        if (productCountMap != null) {
            logger.debug("[updateProductAmounts] Start to update product amounts");
            for (Map.Entry<Product, Integer> entry : productCountMap.entrySet()) {
                Product product = entry.getKey();
                Integer boughtCount = entry.getValue();
                product.setAmount(product.getAmount() - boughtCount);
                productRepository.update(product);
            }
            logger.debug("[updateProductAmounts] Finish to update product amounts");
        }
    }

    private Map<Product, Integer> getProductCountMap(List<Product> products, List<Long> cartIds) {
        Map<Product, Integer> productMap = new HashMap<>();

        for (Product product : products) {
            if (!productMap.containsKey(product)) {
                int productCount = getProductCount(product, cartIds);
                productMap.put(product, productCount);
            }
        }
        return productMap;
    }

    @Override
    protected Repository<Order> getRepository(DataSource dataSource) {
        return getRepositoryFactory()
                .newInstance(OrderRepository.class,
                        dataSource);
    }


    private BigDecimal getProductCostSum(Map<Product, Integer> productMap) {
        if (productMap != null && !productMap.isEmpty()) {
            BigDecimal result = BigDecimal.ZERO;
            for(Map.Entry<Product, Integer> entry : productMap.entrySet()){
                Product product = entry.getKey();
                Integer count = entry.getValue();
                BigDecimal cost = product.getCost();
                BigDecimal itemResult = cost.multiply(new BigDecimal(count));
                result = result.add(itemResult);
            }
            return result;
        }

        return BigDecimal.ZERO;
    }

    private int getProductCount(Product product, List<Long> products) {
        return products
                .stream()
                .filter(prodId -> product.getId() == prodId)
                .collect(Collectors.toList())
                .size();
    }

}
