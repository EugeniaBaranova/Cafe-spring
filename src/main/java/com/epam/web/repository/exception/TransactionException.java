package com.epam.web.repository.exception;

public class TransactionException extends RuntimeException {
    public TransactionException(String message, Throwable cause){
        super(message, cause);
    }
}
