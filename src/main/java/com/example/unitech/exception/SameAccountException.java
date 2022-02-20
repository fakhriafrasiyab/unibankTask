package com.example.unitech.exception;

public class SameAccountException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public SameAccountException(String message) {
        super(message);
    }
}

