package com.epam.web.entity.validation;

import java.io.Serializable;

public class Error implements Serializable {

    private static final long serialVersionUID = 4313238179238520981L;

    private String fieldName;
    private String fieldValue;
    private String message;

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
