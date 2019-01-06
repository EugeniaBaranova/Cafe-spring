package com.epam.web.entity.validation;

public class NullEntityError extends Error {

    @Override
    public String getMessage() {
        return "Object for registration is empty";
    }
}

