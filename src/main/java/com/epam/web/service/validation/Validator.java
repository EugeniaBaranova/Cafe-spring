package com.epam.web.service.validation;

import com.epam.web.entity.validation.ValidationResult;

public interface Validator<T> {

    ValidationResult validate(T entity);

}
