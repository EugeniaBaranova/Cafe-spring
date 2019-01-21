package com.epam.web.repository;

import com.epam.web.repository.converter.OrderConverter;
import com.epam.web.repository.converter.OrderItemConverter;
import com.epam.web.repository.converter.ProductConverter;
import com.epam.web.repository.converter.UserConverter;
import com.epam.web.repository.exception.UnsupportedFactoryTypeException;
import com.epam.web.repository.impl.order.OrderRepositoryImpl;
import com.epam.web.repository.impl.orderItem.OrderItemRepositoryImpl;
import com.epam.web.repository.impl.product.ProductRepositoryImpl;
import com.epam.web.repository.impl.user.UserRepositoryImpl;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class RepositoryFactory {

    private final Map<Class<? extends Repository<?>>, Function<Connection, Repository<?>>> DISPATCHER_MAP
            = new HashMap<>();

    public RepositoryFactory() {
        DISPATCHER_MAP.put(ProductRepository.class, getProductRepository());
        DISPATCHER_MAP.put(UserRepository.class, getUserRepository());
        DISPATCHER_MAP.put(OrderRepository.class, getOrderRepository());
        DISPATCHER_MAP.put(OrderItemRepository.class, getOrderItemRepository());
    }

    public <T extends Repository<?>> T newInstance(Class<T> clazz, Connection connection) {
        Function<Connection, Repository<?>> function = DISPATCHER_MAP.get(clazz);
        if (function == null) {
            throw new UnsupportedFactoryTypeException(clazz);
        }
        return (T) function.apply(connection);
    }

    private Function<Connection, Repository<?>> getProductRepository() {
        return connection -> new ProductRepositoryImpl(connection, new ProductConverter());
    }


    private Function<Connection, Repository<?>> getUserRepository() {
        return connection -> new UserRepositoryImpl(connection, new UserConverter());
    }


    private Function<Connection, Repository<?>> getOrderRepository() {
        return connection -> new OrderRepositoryImpl(connection, new OrderConverter());
    }


    private Function<Connection, Repository<?>> getOrderItemRepository() {
        return connection -> new OrderItemRepositoryImpl(connection, new OrderItemConverter());
    }

}
