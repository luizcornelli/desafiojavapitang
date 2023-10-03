package com.desafiojavapitang.services.exceptions;

public class LicensePlateAlreadyExistsException extends RuntimeException {
    public LicensePlateAlreadyExistsException(String msg) {
        super(msg);
    }
}
