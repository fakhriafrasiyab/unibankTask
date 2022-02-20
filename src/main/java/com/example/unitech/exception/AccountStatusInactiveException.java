package com.example.unitech.exception;

public class AccountStatusInactiveException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public AccountStatusInactiveException(String message) {
        super(message);
    }
}
