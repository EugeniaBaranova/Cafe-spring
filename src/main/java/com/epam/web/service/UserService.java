package com.epam.web.service;

import com.epam.web.entity.RegistrationResult;
import com.epam.web.entity.user.User;
import com.epam.web.service.exception.ServiceException;

import java.util.Optional;

public interface UserService extends Service {

    Optional<User> login(String login, String password) throws ServiceException;

    RegistrationResult register(User user) throws ServiceException;

    RegistrationResult<User> editProfileInfo(Long id, User newUser) throws ServiceException;

}
