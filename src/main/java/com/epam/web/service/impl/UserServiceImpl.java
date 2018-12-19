package com.epam.web.service.impl;

import com.epam.web.entity.User;
import com.epam.web.repository.Repository;
import com.epam.web.repository.exception.RepositoryException;
import com.epam.web.repository.specification.UserByLoginAndPasswordSpec;
import com.epam.web.service.UserService;
import com.epam.web.service.exception.ServiceException;
import org.apache.log4j.Logger;

import java.util.Optional;

public class UserServiceImpl implements UserService {

    private static final Logger logger = Logger.getLogger(UserServiceImpl.class);

    private Repository<User> userRepository;

    public UserServiceImpl(Repository<User> userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> login(String login, String password) throws ServiceException {
        try {
            if (login!=null & password!=null){
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
            getUserRepository().add(user);
        } catch (RepositoryException e) {
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
        //TODO better
        return null;
    }

    private Repository<User> getUserRepository() {
        return userRepository;
    }
}
