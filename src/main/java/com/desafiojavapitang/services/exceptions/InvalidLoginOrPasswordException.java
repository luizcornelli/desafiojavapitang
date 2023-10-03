package com.desafiojavapitang.services.exceptions;

public class InvalidLoginOrPasswordException extends RuntimeException {
    public InvalidLoginOrPasswordException(String msg) {
        super(msg);
    }
}
