package com.epam.web.repository.factory;


import com.epam.web.repository.*;
import com.epam.web.repository.exception.UnsupportedFactoryTypeException;
import com.epam.web.repository.impl.ProductRepositoryImpl;
import com.epam.web.repository.impl.UserRepositoryImpl;

import java.util.HashMap;
import java.util.Map;

public final class RepositoryFactory {
    private final static RepositoryFactory INSTANCE = new RepositoryFactory();
    private UserRepository userRepository = new UserRepositoryImpl();
    private ProductRepository productRepository = new ProductRepositoryImpl();
    private final Map<Class<? extends Repository>, Repository> repositories
            = new HashMap<>();


    private RepositoryFactory() {
        repositories.put(UserRepository.class, userRepository);
        repositories.put(ProductRepository.class, productRepository);
    }

    public static RepositoryFactory getInstance() {
        return INSTANCE;
    }



    public <T> T getRepository(Class<T> repositoryClass){
        Repository repository = repositories.get(repositoryClass);
        if(repository == null){
            throw new UnsupportedFactoryTypeException(repositoryClass);
        }
        return (T) repository;
    }
}
