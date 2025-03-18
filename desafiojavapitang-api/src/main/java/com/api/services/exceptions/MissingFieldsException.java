package com.api.services.exceptions;

public class MissingFieldsException extends RuntimeException {
    public MissingFieldsException(String msg) {
        super(msg);
    }
}
