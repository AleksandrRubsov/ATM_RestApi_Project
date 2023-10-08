package ru.ruba.ATM_project.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ruba.ATM_project.dto.AccountDTO;
import ru.ruba.ATM_project.models.Account;
import ru.ruba.ATM_project.repository.AccountRepository;
import ru.ruba.ATM_project.service.AccountService;
import java.math.BigDecimal;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account createAccount(String name, String pinCode) {

        // Проверяем, что PIN-код состоит из 4 цифр
        if (!isValidPinCode(pinCode)) {
            throw new IllegalArgumentException("Invalid PIN code format. It must be 4 digits.");
        }
        Account account = new Account();

        // Генерируем уникальный номер счета
        String accountNumber = generateAccountNumber();

        account.setName(name);
        account.setPinCode(pinCode);
        account.setAccountNumber(accountNumber);
        account.setBalance(BigDecimal.valueOf(0.0));

        // Сохраняем счет в репозитории
        return accountRepository.save(account);
    }

    public List<AccountDTO> getAllAccounts() {
        List<Account> accounts = accountRepository.findAll();
        return accounts.stream()
                .map(account -> new AccountDTO(account.getName(), account.getAccountNumber(), account.getBalance()))
                .collect(Collectors.toList());
    }

    public Account getAccountByNumber(String number) {
        return accountRepository.findByAccountNumber(number);
    }

    public String generateAccountNumber() {
        String accountNumber;
        Account existingAccount;
        do {
            // Генерируем случайный номер счета
            Random random = new Random();
            int accountNumberInt = random.nextInt(1000000);
            accountNumber = String.format("%06d", accountNumberInt);

            // Проверяем, существует ли счет с таким номером
            existingAccount = accountRepository.findByAccountNumber(accountNumber);
        } while (existingAccount != null);

        return accountNumber;
    }

    static boolean isValidPinCode(String pinCode) {
        // Проверяем, что PIN-код состоит из 4 цифр
        return pinCode != null && pinCode.matches("\\d{4}");
    }

}

