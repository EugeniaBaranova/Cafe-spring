package com.epam.web.service.validation;

import com.epam.web.entity.order.Order;
import com.epam.web.entity.validation.ValidationResult;

public class OrderValidator extends AbstractValidator<Order> {

    public OrderValidator() {

    }

    @Override
    public ValidationResult validate(Order order) {
        return super.validate(order);
    }


}
