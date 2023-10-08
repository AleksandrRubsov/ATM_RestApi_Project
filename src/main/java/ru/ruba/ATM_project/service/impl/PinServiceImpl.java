package ru.ruba.ATM_project.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ruba.ATM_project.models.Account;
import ru.ruba.ATM_project.repository.AccountRepository;
import ru.ruba.ATM_project.service.PinService;

@Service
public class PinServiceImpl implements PinService {

    private final AccountRepository accountRepository;

    public PinServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Transactional
    public boolean isPinValidForAccount(String accountNumber, String enteredPin) {
        Account account = accountRepository.findByAccountNumber(accountNumber);

        if (account == null) {
            return false; // Аккаунт не найден
        }

        return enteredPin != null && enteredPin.equals(account.getPinCode());
    }

}
