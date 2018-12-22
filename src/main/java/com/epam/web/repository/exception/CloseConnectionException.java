package com.epam.web.repository.exception;

public class CloseConnectionException extends RuntimeException {

    public CloseConnectionException() {
    }

    public CloseConnectionException(String message) {
        super(message);
    }

    public CloseConnectionException(String message, Throwable cause) {
        super(message, cause);
    }

    public CloseConnectionException(Throwable cause) {
        super(cause);
    }
}
