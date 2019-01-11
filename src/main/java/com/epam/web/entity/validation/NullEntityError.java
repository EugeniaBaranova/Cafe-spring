package com.epam.web.entity.validation;

public class NullEntityError extends Error {

    private static final long serialVersionUID = -5791490148260509003L;

    @Override
    public String getMessage() {
        return "Object for registration is empty";
    }
}

