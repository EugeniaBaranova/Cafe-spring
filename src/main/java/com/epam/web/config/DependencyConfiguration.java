package com.epam.web.config;

import com.epam.web.entity.order.Order;
import com.epam.web.entity.order.OrderItem;
import com.epam.web.entity.product.Product;
import com.epam.web.entity.user.User;
import com.epam.web.repository.RepositoryFactory;
import com.epam.web.repository.connection.RepositorySource;
import com.epam.web.repository.connection.pool.BaseConnectionPool;
import com.epam.web.service.OrderService;
import com.epam.web.service.ProductService;
import com.epam.web.service.Service;
import com.epam.web.service.UserService;
import com.epam.web.service.factory.ServiceFactory;
import com.epam.web.service.impl.order.OrderServiceImpl;
import com.epam.web.service.impl.product.ProductServiceImpl;
import com.epam.web.service.impl.user.UserServiceImpl;
import com.epam.web.service.validation.*;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class DependencyConfiguration {

    public void configure() {
        this.configureServiceFactory();

    }

    private RepositoryFactory repositoryFactory(){
        return new RepositoryFactory();
    }

    private RepositorySource repositorySource(){
        return BaseConnectionPool.getInstance();
    }

    private Validator<User> userValidator() {
        return new UserValidator();
    }

    private Validator<Product> productValidator(){
        return new ProductValidator();
    }

    private Validator<Order> orderValidator(){
        return new OrderValidator();
    }

    private Validator<OrderItem> orderItemValidator(){
        return new OrderItemValidator();
    }




    private UserService userService() {
        return new UserServiceImpl(
                repositoryFactory(),
                repositorySource(),
                userValidator()
        );
    }

    private ProductService productService() {
        return new ProductServiceImpl(
                repositoryFactory(),
                repositorySource(),
                productValidator());
    }

    private OrderService orderService(){
        return new OrderServiceImpl(
                repositoryFactory(),
                repositorySource(),
                orderValidator()
        );
    }

    private ServiceFactory configureServiceFactory() {
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        Map<Class<? extends Service>, Service> serviceMap = getServiceMap();
        Class<? extends ServiceFactory> factoryClass = serviceFactory.getClass();
        Field[] fields = factoryClass.getDeclaredFields();
        for (Field field : fields) {
            Class<?> fieldTypeClass = field.getType();
            if (Map.class.equals(fieldTypeClass)) {
                field.setAccessible(true);
                try {
                    field.set(serviceFactory, serviceMap);
                    break;
                } catch (IllegalAccessException e) {
                    //TODO my runtime exception
                    throw new RuntimeException(e);
                }
            }
        }
        return serviceFactory;
    }





    private Map<Class<? extends Service>, Service> getServiceMap() {
        Map<Class<? extends Service>, Service> serviceMap = new HashMap<>();
        serviceMap.put(ProductService.class, productService());
        serviceMap.put(UserService.class, userService());
        serviceMap.put(OrderService.class, orderService());
        return serviceMap;
    }

}
