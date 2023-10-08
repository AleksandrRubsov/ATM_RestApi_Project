package ru.ruba.ATM_project.service;

public interface PinService {
    boolean isPinValidForAccount(String accountNumber, String enteredPin);
}
