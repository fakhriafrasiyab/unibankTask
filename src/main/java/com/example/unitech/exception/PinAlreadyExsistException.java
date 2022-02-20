package com.example.unitech.exception;

public class PinAlreadyExsistException extends AlreadyExistsException{

    private static final long serialVersionUID = 1L;
    private static final String MESSAGE = "PIN_ALREADY_EXCEPTION";

    public PinAlreadyExsistException() {
        super(MESSAGE);
    }
}
