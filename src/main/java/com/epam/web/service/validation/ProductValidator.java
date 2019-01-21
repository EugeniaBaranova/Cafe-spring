package com.epam.web.service.validation;

import com.epam.web.entity.product.Product;
import com.epam.web.entity.validation.ValidationResult;

public class ProductValidator implements Validator<Product> {
    @Override
    public ValidationResult validate(Product entity) {
        return new ValidationResult();
    }
}
