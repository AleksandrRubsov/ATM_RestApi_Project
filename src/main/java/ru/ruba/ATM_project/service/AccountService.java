package ru.ruba.ATM_project.service;

import ru.ruba.ATM_project.dto.AccountDTO;
import ru.ruba.ATM_project.models.Account;

import java.util.List;

public interface AccountService {
    Account createAccount(String name, String pinCode);

    List<AccountDTO> getAllAccounts();

    Account getAccountByNumber(String number);

    String generateAccountNumber();



}