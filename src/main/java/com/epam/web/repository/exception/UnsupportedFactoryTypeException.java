package com.epam.web.repository.exception;

public class UnsupportedFactoryTypeException extends RuntimeException {

    private Class unsupportedClass;

    public UnsupportedFactoryTypeException(Class clazz){
        super();
        this.unsupportedClass = clazz;
    }

    @Override
    public String getMessage() {
        return "Error creating repository by class "+unsupportedClass.getName()+"." +
                "Is not supported repository class";
    }
}
