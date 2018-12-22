package com.epam.web.repository.exception;

public class ConnectionPoolInitializationException extends RuntimeException{

    public ConnectionPoolInitializationException() {
    }

    public ConnectionPoolInitializationException(String message) {
        super(message);
    }

    public ConnectionPoolInitializationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConnectionPoolInitializationException(Throwable cause) {
        super(cause);
    }
}
