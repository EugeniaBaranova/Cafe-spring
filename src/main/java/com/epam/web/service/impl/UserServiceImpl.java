package com.epam.web.service.impl;

import com.epam.web.entity.User;
import com.epam.web.repository.Repository;
import com.epam.web.repository.exception.RepositoryException;
import com.epam.web.repository.specification.UserByLoginAndPasswordSpec;
import com.epam.web.service.UserService;
import com.epam.web.service.exception.ServiceException;
import org.apache.log4j.Logger;

import java.util.Optional;
import java.util.concurrent.locks.ReentrantLock;

public class UserServiceImpl implements UserService {
    private static final Logger logger = Logger.getLogger(UserServiceImpl.class);
    private Repository<User> userRepository;
    private ReentrantLock reentrantLock;

    @Override
    public Optional<User> login(String login, String password) throws ServiceException {
        try {
            if (login != null & password != null) {
                return getUserRepository().queryForSingleResult(new UserByLoginAndPasswordSpec(login, password));
            }
            return Optional.empty();
        } catch (RepositoryException e) {
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public User addUser(User user) throws ServiceException {
        try {
            if(user != null){
                return getUserRepository().add(user);
            }
            return user;
        } catch (RepositoryException e) {
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    private Repository<User> getUserRepository() {
        return userRepository;
    }

    public void setUserRepository(Repository<User> userRepository) {
        this.userRepository = userRepository;
    }

    public void setReentrantLock(ReentrantLock reentrantLock) {
        this.reentrantLock = reentrantLock;
    }
}
