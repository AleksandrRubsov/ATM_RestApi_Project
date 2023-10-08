package ru.ruba.ATM_project.exception;

public class NotFoundException extends RuntimeException {

    private static final long serialVersionUID = 2L;

    public NotFoundException(String message) {
        super(message);
    }
}