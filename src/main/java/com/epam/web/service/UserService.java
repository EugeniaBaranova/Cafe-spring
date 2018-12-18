package com.epam.web.service;

import com.epam.web.entity.User;
import com.epam.web.service.exception.ServiceException;

import java.util.Optional;

public interface UserService {

    Optional<User> login(String login, String password) throws ServiceException;

    User addUser(User user) throws ServiceException;
}
