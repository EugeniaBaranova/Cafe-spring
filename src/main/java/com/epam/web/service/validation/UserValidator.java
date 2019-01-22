package com.epam.web.service.validation;

import com.epam.web.entity.user.User;
import com.epam.web.entity.validation.Error;
import com.epam.web.entity.validation.ValidationResult;

import java.util.*;
import java.util.function.Function;

import static com.epam.web.repository.converter.Fields.User.LOGIN;
import static com.epam.web.repository.converter.Fields.User.NAME;

public class UserValidator extends AbstractValidator<User> {

    private static final String NAME_PATTERN = "^[a-zA-Zа-яА-Я ]{5,30}$";
    private static final String LOGIN_PATTERN = "^[a-zA-Zа-яА-Я0-9 ]{5,30}$";
    private static final String EMAIL_PATTERN = "^(([^<>()[\\]\\\\.,;:\\s@\\\"]+(\\.[^<>()[\\]\\\\.,;:\\s@\\\"]+)*)|(\\\".+\\\"))@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
    private static final String PASSWORD_PATTERN = "^(((?=.*[a-z])(?=.*[A-Z]))|((?=.*[a-z])(?=.*[0-9]))|((?=.*[A-Z])(?=.*[0-9])))(?=.{6,})/";

    public UserValidator() {
        result.add(checkForName());
        result.add(checkForLogin());
        result.add(checkForEmail());
        result.add(checkForPassword());
    }

    @Override
    public ValidationResult validate(User user) {
        return super.validate(user);
    }

    private Function<User, Optional<Error>> checkForName() {
        return user -> {
            Error error = new Error();
            String userName = user.getName();
            error.setFieldName(NAME);
            error.setFieldValue(userName);
            if (isEmpty(userName, error, "registration.message.empty_name")
                    || isNotMatch(userName, NAME_PATTERN, error, "registration.message.name_not_like_regexp")) {
                return Optional.of(error);
            }
            return Optional.empty();
        };
    }

    private Function<User, Optional<Error>> checkForLogin() {
        return user -> {
            Error error = new Error();
            String userLogin = user.getLogin();
            error.setFieldName(LOGIN);
            error.setFieldValue(userLogin);
            if (isEmpty(userLogin, error, "registration.message.empty_login")
                    || isNotMatch(userLogin, LOGIN_PATTERN, error, "registration.message.login_not_like_regexp")) {
                return Optional.of(error);
            }
            return Optional.empty();
        };
    }

    private Function<User, Optional<Error>> checkForPassword() {
        return user -> {
            Error error = new Error();
            String userPassword = user.getPassword();
            error.setFieldName(LOGIN);
            error.setFieldValue(userPassword);
            if (isEmpty(userPassword, error, "registration.message.empty_password")
                    || isNotMatch(userPassword, PASSWORD_PATTERN, error, "registration.message.password_not_like_regexp")) {
                return Optional.of(error);
            }
            return Optional.empty();
        };
    }

    private Function<User, Optional<Error>> checkForEmail() {
        return user -> {
            Error error = new Error();
            String userEmail = user.getEmail();
            error.setFieldName(LOGIN);
            error.setFieldValue(userEmail);
            if (isEmpty(userEmail, error, "registration.message.empty_email")
                    || isNotMatch(userEmail, EMAIL_PATTERN, error, "registration.message.email_not_like_regexp")) {
                return Optional.of(error);
            }
            return Optional.empty();
        };
    }
}
