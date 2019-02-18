package com.epam.web.service.validation;

import com.epam.web.entity.order.OrderItem;
import com.epam.web.entity.validation.ValidationResult;
import org.springframework.stereotype.Component;

@Component
public class OrderItemValidator implements Validator<OrderItem> {
    @Override
    public ValidationResult validate(OrderItem entity) {
        return new ValidationResult();
    }
}
