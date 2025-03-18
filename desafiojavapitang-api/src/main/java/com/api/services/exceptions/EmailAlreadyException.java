package com.api.services.exceptions;

public class EmailAlreadyException extends RuntimeException {
    public EmailAlreadyException(String msg) {
        super(msg);
    }
}
