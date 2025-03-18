package com.api.services.exceptions;

public class InvalidTokenJWTExeption extends RuntimeException {
    public InvalidTokenJWTExeption(String msg) {
        super(msg);
    }
}
