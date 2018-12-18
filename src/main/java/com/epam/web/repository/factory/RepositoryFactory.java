package com.epam.web.repository.factory;


import com.epam.web.entity.Product;
import com.epam.web.entity.User;
import com.epam.web.repository.ProductRepository;
import com.epam.web.repository.Repository;
import com.epam.web.repository.UserRepository;
import com.epam.web.repository.connections.ConnectionWrapper;

public class RepositoryFactory {

    public static Repository<User> getUserRepository(){
        return new UserRepository();
    }

    public static Repository<Product> getProductRepository(){
        return new ProductRepository();
    }
}
