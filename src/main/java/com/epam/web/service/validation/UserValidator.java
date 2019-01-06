package com.epam.web.service.validation;

import com.epam.web.entity.user.User;
import com.epam.web.entity.validation.Error;
import com.epam.web.entity.validation.NullEntityError;
import com.epam.web.entity.validation.ValidationResult;
import com.epam.web.utils.StringUtils;

import java.util.*;
import java.util.function.Function;

public class UserValidator implements Validator<User> {
    private List<Function<User, Optional<Error>>> result = new ArrayList<>();

    public UserValidator() {
        result.add(checkForName());

    }

    @Override
    public ValidationResult validate(User user) {
        Set<Error> errors = new HashSet<>();
        Optional<Error> nullEntityError = checkForNull(user);
        if(nullEntityError.isPresent()){
            errors.add(nullEntityError.get());
            return new ValidationResult(errors);
        }

        ValidationResult validationResult = new ValidationResult();
        List<Function<User, Optional<Error>>> errorResult = getResult();

        for(Function<User, Optional<Error>> function : errorResult){
            Optional<Error> checkResult = function.apply(user);
            checkResult.ifPresent(errors::add);
        }
        validationResult.setErrors(errors);
        return validationResult;
    }


    private Optional<Error> checkForNull(User user){
        if(user == null){
            return Optional.of(new NullEntityError());
        }
        return Optional.empty();
    }

    private Function<User, Optional<Error>> checkForName(){
        return user -> {
            Error error = new Error();
            error.setFieldName("name");
            error.setFieldValue(user.getName());
            if(StringUtils.isEmpty(user.getName())){
                error.setMessage("registration.validation.message.empty_name");
                return Optional.of(error);
            }
            return Optional.empty();
        };
    }

    private List<Function<User, Optional<Error>>> getResult() {
        return result;
    }
}
