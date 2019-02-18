package com.epam.web.service.validation;

import com.epam.web.entity.product.Product;
import com.epam.web.entity.validation.Error;
import com.epam.web.entity.validation.ValidationResult;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;

import static com.epam.web.repository.converter.Fields.Product.*;

@Component
public class ProductValidator extends AbstractValidator<Product> {

    private static final String NAME_AND_DESCRIPTION_PATTERN = "^[a-zA-Zа-яА-Я0-9()\\-\\_., ]{5,30}$";

    public ProductValidator() {
        result.add(checkForName());
        result.add(checkForDescription());
        result.add(checkForCost());
        result.add(checkForAmount());
        result.add(checkForImage());
    }

    @Override
    public ValidationResult validate(Product product) {
        return super.validate(product);
    }

    private Function<Product, Optional<Error>> checkForName() {
        return product -> {
            Error error = new Error();
            String productName = product.getName();
            error.setFieldName(NAME);
            error.setFieldValue(productName);
            if (isEmpty(productName, error, "message.empty_name")
                    || isNotMatch(productName, NAME_AND_DESCRIPTION_PATTERN, error, "message.name_not_like_regexp")) {
                return Optional.of(error);
            }
            return Optional.empty();
        };
    }

    private Function<Product, Optional<Error>> checkForDescription() {
        return product -> {
            Error error = new Error();
            String productName = product.getDescription();
            error.setFieldName(DESCRIPTION);
            error.setFieldValue(productName);
            if (isEmpty(productName, error, "message.empty_description")) {
                return Optional.of(error);
            }
            return Optional.empty();
        };
    }


    private Function<Product, Optional<Error>> checkForCost() {
        return product -> {
            Error error = new Error();
            BigDecimal productCost = product.getCost();
            error.setFieldName(COST);
            if (productCost == null) {
                error.setMessage("message.empty_cost");
                return Optional.of(error);
            }
            error.setFieldValue(productCost.toString());
            if (productCost.doubleValue() <= 0) {
                error.setMessage("message.under_zero_cost");
                return Optional.of(error);
            }
            return Optional.empty();
        };
    }

    private Function<Product, Optional<Error>> checkForAmount() {
        return product -> {
            Error error = new Error();
            int productAmount = product.getAmount();
            error.setFieldName(AMOUNT);
            error.setFieldValue(String.valueOf(productAmount));
            if (productAmount <= 0) {
                error.setMessage("message.not_positive_amount");
                return Optional.of(error);
            }
            return Optional.empty();
        };
    }

    private Function<Product, Optional<Error>> checkForImage() {
        return product -> {
            Error error = new Error();
            byte[] productImage = product.getImage();
            error.setFieldName(IMAGE);
            if (productImage == null) {
                error.setMessage("message.empty_image");
                return Optional.of(error);
            }
            if (productImage.length > 10485760) {
                error.setMessage("message.upper_ten_mb_image");
                return Optional.of(error);
            }
            return Optional.empty();
        };
    }
}
