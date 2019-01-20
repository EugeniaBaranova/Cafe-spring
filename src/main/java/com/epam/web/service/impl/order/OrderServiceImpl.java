package com.epam.web.service.impl.order;

import com.epam.web.entity.enums.OrderState;
import com.epam.web.entity.enums.PaymentMethod;
import com.epam.web.entity.order.Order;
import com.epam.web.entity.order.OrderContext;
import com.epam.web.entity.order.OrderItem;
import com.epam.web.entity.product.Product;
import com.epam.web.entity.user.User;
import com.epam.web.repository.OrderRepository;
import com.epam.web.repository.ProductRepository;
import com.epam.web.repository.connection.pool.SingleConnectionPool;
import com.epam.web.repository.converter.OrderConverter;
import com.epam.web.repository.converter.ProductConverter;
import com.epam.web.repository.impl.order.OrderRepositoryImpl;
import com.epam.web.repository.impl.product.ProductRepositoryImpl;
import com.epam.web.service.OrderService;
import com.epam.web.service.exception.ServiceException;
import com.epam.web.utils.TransactionUtils;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderServiceImpl(ProductRepository productRepository,
                            OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    @Override
    public Order makeOrder(OrderContext orderContext) throws ServiceException {
        SingleConnectionPool connectionPool = new SingleConnectionPool();
        try {
            TransactionUtils.beginTransaction(connectionPool);

            //TODO or fill fields?
            ProductRepository productRepository = new ProductRepositoryImpl(connectionPool, new ProductConverter());
            OrderRepository orderRepository = new OrderRepositoryImpl(connectionPool, new OrderConverter());

            List<Product> products = orderContext.getProducts();
            if (products != null) {
                Set<OrderItem> orderItems = new HashSet<>();
                BigDecimal sum = new BigDecimal(0);
                for (Product product : products) {
                    OrderItem orderItem = new OrderItem();
                    orderItem.setMealId(product.getId());
                    int productCount = getProductCount(product, products);
                    orderItem.setCount(productCount);
                    orderItems.add(orderItem);
                    //TODO result of addition
                    sum.add(product.getCost());
                }
                Order order = new Order();
                order.setSum(sum);
                User customer = orderContext.getCustomer();
                if (customer != null) {
                    order.setUserId(customer.getId());
                }
                PaymentMethod paymentMethod = orderContext.getPaymentMethod();
                order.setPaymentMethod(paymentMethod);
                Date receiving = orderContext.getReceiving();
                if (receiving != null) {
                    order.setReceivingDate(receiving.toString());
                }
                order.setOrderState(OrderState.WAITING);
                order.setOrderDate(new Date().toString());

                TransactionUtils.commit(connectionPool, true);
            }
            return null;
        } catch (SQLException e) {
            //TODO better way for Exception handling
            try {
                TransactionUtils.rollbackTransaction(connectionPool, false);
            } catch (SQLException ex) {
                throw new ServiceException(ex.getMessage(), ex);
            }
        }
        return null;
    }

    private int getProductCount(Product product, List<Product> products) {
        return products
                .stream()
                .filter(product1 -> product1.equals(product))
                .collect(Collectors.toList())
                .size();
    }

    private OrderRepository getOrderRepository() {
        return orderRepository;
    }

    private ProductRepository getProductRepository() {
        return productRepository;
    }

}
