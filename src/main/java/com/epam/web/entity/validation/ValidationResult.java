package com.epam.web.entity.validation;

import java.util.HashSet;
import java.util.Set;

public class ValidationResult {

    private Set<Error> errors = new HashSet<>();

    public ValidationResult(Set<Error> errors) {
        this.errors = errors;
    }

    public ValidationResult() {
    }

    public boolean hasError(){
        return !errors.isEmpty();
    };

    public Set<Error> getErrors(){
        return new HashSet<>(errors);
    };

    public void setErrors(Set<Error> errors) {
        this.errors = errors;
    }
}
