package com.epam.web.entity;

import com.epam.web.entity.validation.Error;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class SavingResult<T> {
    private T data;
    private Set<Error> errors = new HashSet<>();

    public SavingResult(T data) {
        this.data = data;
    }

    public SavingResult(Set<Error> errors) {
        if(errors != null){
            this.errors = errors;
        }
    }

    public SavingResult() {
    }
    public boolean hasError(){
        return !errors.isEmpty();
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Set<Error> getErrors() {
        return new HashSet<>(errors);
    }

    public void setErrors(Set<Error> errors) {
        if(errors != null){
            this.errors = errors;
        }
    }



}
