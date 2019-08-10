package com.xenosgrilda.app.exceptions;

public class CustomerEmptyBodyException extends RuntimeException {

    public CustomerEmptyBodyException(String message) {
        super(message);
    }

    public CustomerEmptyBodyException(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomerEmptyBodyException(Throwable cause) {
        super(cause);
    }
}
