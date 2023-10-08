package ru.ruba.ATM_project.exception;

public class UnauthorizedException extends RuntimeException {

    private static final long serialVersionUID = -5783320765855582306L;

    public UnauthorizedException(String message) {
        super(message);
    }
}