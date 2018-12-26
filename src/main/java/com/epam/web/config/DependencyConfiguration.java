package com.epam.web.config;

import com.epam.web.entity.Product;
import com.epam.web.entity.User;
import com.epam.web.repository.ProductRepository;
import com.epam.web.repository.UserRepository;
import com.epam.web.repository.connections.ConnectionPool;
import com.epam.web.repository.converter.Converter;
import com.epam.web.repository.converter.ProductConverter;
import com.epam.web.repository.converter.UserConverter;
import com.epam.web.repository.impl.ProductRepositoryImpl;
import com.epam.web.repository.impl.UserRepositoryImpl;
import com.epam.web.service.ProductService;
import com.epam.web.service.Service;
import com.epam.web.service.UserService;
import com.epam.web.service.factory.ServiceFactory;
import com.epam.web.service.impl.ProductServiceImpl;
import com.epam.web.service.impl.UserServiceImpl;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public class DependencyConfiguration {

    public void configure(){
        this.configureServiceFactory();

    }

    private Converter<Product> productConverter() {
        return new ProductConverter();
    }

    private Converter<User> userConverter() {
        return new UserConverter();
    }

    private ConnectionPool connectionPool() {
        return ConnectionPool.getInstance();
    }

    private ProductRepository productRepository() {
        return new ProductRepositoryImpl(connectionPool(),productConverter());
    }

    private UserRepository userRepository() {
        return new UserRepositoryImpl(connectionPool(),userConverter());
    }


    private UserService userService() {
        UserServiceImpl userService = new UserServiceImpl();
        userService.setUserRepository(userRepository());
        userService.setReentrantLock(new ReentrantLock());
        return userService;
    }

    private ProductService productService() {
        ProductServiceImpl productService = new ProductServiceImpl();
        productService.setProductRepository(productRepository());
        productService.setReentrantLock(new ReentrantLock());
        return productService;
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
        return serviceMap;
    }

}
