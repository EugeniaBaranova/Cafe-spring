package com.epam.web.repository;

import com.epam.web.repository.converter.*;
import com.epam.web.repository.exception.UnsupportedFactoryTypeException;
import com.epam.web.repository.impl.order.OrderRepositoryImpl;
import com.epam.web.repository.impl.orderItem.OrderItemRepositoryImpl;
import com.epam.web.repository.impl.product.ProductRepositoryImpl;
import com.epam.web.repository.impl.user.UserRepositoryImpl;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class RepositoryFactory {

    private final Map<Class<? extends Repository<?>>, Function<DataSource, Repository<?>>> DISPATCHER_MAP
            = new HashMap<>();

    public RepositoryFactory() {
        DISPATCHER_MAP.put(ProductRepository.class, getProductRepository());
        DISPATCHER_MAP.put(UserRepository.class, getUserRepository());
        DISPATCHER_MAP.put(OrderRepository.class, getOrderRepository());
        DISPATCHER_MAP.put(OrderItemRepository.class, getOrderItemRepository());
    }

    public <T extends Repository<?>> T newInstance(Class<T> clazz, DataSource dataSource) {
        Function<DataSource, Repository<?>> function = DISPATCHER_MAP.get(clazz);
        if (function == null) {
            throw new UnsupportedFactoryTypeException(clazz);
        }
        return (T) function.apply(dataSource);
    }

    private Function<DataSource, Repository<?>> getProductRepository() {
        return dataSource -> new ProductRepositoryImpl(dataSource, new ProductRowMapper());
    }


    private Function<DataSource, Repository<?>> getUserRepository() {
        return dataSource -> new UserRepositoryImpl(dataSource, new UserRowMapper());
    }


    private Function<DataSource, Repository<?>> getOrderRepository() {
        return dataSource -> new OrderRepositoryImpl(dataSource, new OrderRowMapper());
    }


    private Function<DataSource, Repository<?>> getOrderItemRepository() {
        return dataSource -> new OrderItemRepositoryImpl(dataSource, new OrderItemRowMapper());
    }

}
