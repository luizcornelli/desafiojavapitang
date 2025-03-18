package com.api.services.exceptions;

public class LoginAlreadyException extends RuntimeException {
    public LoginAlreadyException(String msg) {
        super(msg);
    }
}
