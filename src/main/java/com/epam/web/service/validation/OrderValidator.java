package com.epam.web.service.validation;

import com.epam.web.entity.order.Order;
import com.epam.web.entity.validation.ValidationResult;

public class OrderValidator implements Validator<Order> {
    @Override
    public ValidationResult validate(Order entity) {
        return new ValidationResult();
    }
}
