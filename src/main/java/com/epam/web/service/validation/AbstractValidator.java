package com.epam.web.service.validation;

import com.epam.web.entity.Entity;
import com.epam.web.entity.validation.Error;
import com.epam.web.entity.validation.NullEntityError;
import com.epam.web.entity.validation.ValidationResult;
import com.epam.web.utils.StringUtils;

import java.util.*;
import java.util.function.Function;

public abstract class AbstractValidator<T extends Entity> implements Validator<T> {

    protected List<Function<T, Optional<Error>>> result = new ArrayList<>();

    @Override
    public ValidationResult validate(T entity) {
        Set<Error> errors = new HashSet<>();
        Optional<Error> nullEntityError = checkForNull(entity);
        if (nullEntityError.isPresent()) {
            errors.add(nullEntityError.get());
            return new ValidationResult(errors);
        }
        ValidationResult validationResult = new ValidationResult();
        List<Function<T, Optional<Error>>> errorResult = getResult();
        for (Function<T, Optional<Error>> function : errorResult) {
            Optional<Error> checkResult = function.apply(entity);
            checkResult.ifPresent(errors::add);
        }
        validationResult.setErrors(errors);
        return validationResult;
    }

    protected Optional<Error> checkForNull(T entity) {
        if (entity == null) {
            return Optional.of(new NullEntityError());
        }
        return Optional.empty();
    }

    protected boolean isEmpty(String field, Error error, String message) {
        if (error != null && message != null) {
            if (StringUtils.isEmpty(field)) {
                error.setMessage(message);
                return true;
            }
        }
        return false;
    }

    protected boolean isNotMatch(String field, String regExp, Error error, String message) {
        if (field != null && regExp != null && error != null && message != null) {
            if (field.matches(regExp)) {
                return false;
            } else {
                error.setMessage(message);
            }
        }
        return true;
    }

    private List<Function<T, Optional<Error>>> getResult() {
        return result;
    }
}
