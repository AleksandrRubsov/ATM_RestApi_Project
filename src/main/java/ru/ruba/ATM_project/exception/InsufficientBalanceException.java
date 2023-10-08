package ru.ruba.ATM_project.exception;

public class InsufficientBalanceException extends RuntimeException {

    private static final long serialVersionUID = 2L;

    public InsufficientBalanceException(String message) {
        super(message);
    }
}