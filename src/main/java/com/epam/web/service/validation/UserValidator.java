package com.epam.web.service.validation;

import com.epam.web.entity.user.User;
import com.epam.web.entity.validation.Error;
import com.epam.web.entity.validation.ValidationResult;
import com.epam.web.utils.StringUtils;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Function;

import static com.epam.web.repository.converter.Fields.User.LOGIN;
import static com.epam.web.repository.converter.Fields.User.NAME;

@Component
public class UserValidator extends AbstractValidator<User> {

    private static final String NAME_PATTERN = "^[a-zA-Zа-яА-Я ]{5,30}$";
    private static final String LOGIN_PATTERN = "^[a-zA-Zа-яА-Я0-9 ]{5,30}$";
    private static final String EMAIL_PATTERN = "([a-zA-Z0-9]+)(@)([a-zA-Z]+)(\\.)([a-zA-Z]){2,3}";

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
            String userPassword = user.getPassword();
            if(StringUtils.isNotEmpty(userPassword)
                    && userPassword.length() < 6){
                Error error = new Error();
                error.setFieldName(LOGIN);
                error.setFieldValue(userPassword);
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
