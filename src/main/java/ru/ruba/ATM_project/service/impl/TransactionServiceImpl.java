package ru.ruba.ATM_project.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ruba.ATM_project.dto.TransactionDTO;
import ru.ruba.ATM_project.exception.InsufficientBalanceException;
import ru.ruba.ATM_project.exception.NotFoundException;
import ru.ruba.ATM_project.exception.UnauthorizedException;
import ru.ruba.ATM_project.mapper.TransactionMapper;
import ru.ruba.ATM_project.models.Account;
import ru.ruba.ATM_project.models.Transaction;
import ru.ruba.ATM_project.repository.AccountRepository;
import ru.ruba.ATM_project.repository.TransactionRepository;
import ru.ruba.ATM_project.service.PinService;
import ru.ruba.ATM_project.service.TransactionService;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static ru.ruba.ATM_project.service.impl.AccountServiceImpl.isValidPinCode;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final AccountRepository accountRepository;

    private final TransactionRepository transactionRepository;

    private final PinService pinService;

    private final TransactionMapper transactionMapper;


    @Autowired
    public TransactionServiceImpl(AccountRepository accountRepository, TransactionRepository transactionRepository, PinService pinService, TransactionMapper transactionMapper) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.pinService = pinService;
        this.transactionMapper = transactionMapper;
    }

    private void validatePin(String accountNumber, String pinCode) {
        if (!isValidPinCode(pinCode) || !pinService.isPinValidForAccount(accountNumber, pinCode)) {
            throw new UnauthorizedException("Invalid PIN");
        }
    }

    @Override
    public void cashDeposit(String accountNumber, String pinCode, BigDecimal amount) {

        Account account = accountRepository.findByAccountNumber(accountNumber);
        if (account == null) {
            throw new NotFoundException("Account not found");

        }

        validatePin(accountNumber, pinCode);

        BigDecimal currentBalance = account.getBalance();
        BigDecimal newBalance = currentBalance.add(amount);
        account.setBalance(newBalance);
        accountRepository.save(account);

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setTransaction_type("Deposit");
        transaction.setTransaction_date(new Date());
        transaction.setSourceAccount(account);
        transactionRepository.save(transaction);
    }

    @Override
    public void cashWithdrawal(String accountNumber, String pinCode, BigDecimal amount) {

        Account account = accountRepository.findByAccountNumber(accountNumber);
        if (account == null) {
            throw new NotFoundException("Account not found");
        }

        validatePin(accountNumber, pinCode);

        BigDecimal currentBalance = account.getBalance();
        if (currentBalance.compareTo(amount) < 0) {
            throw new InsufficientBalanceException("Insufficient balance");
        }

        BigDecimal newBalance = currentBalance.subtract(amount);
        account.setBalance(newBalance);
        accountRepository.save(account);

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setTransaction_type("Withdrawal");
        transaction.setTransaction_date(new Date());
        transaction.setSourceAccount(account);
        transactionRepository.save(transaction);

    }

    @Override
    public void fundTransfer(String sourceAccountNumber, String targetAccountNumber, String pinCode, BigDecimal amount) {

        Account sourceAccount = accountRepository.findByAccountNumber(sourceAccountNumber);
        if (sourceAccount == null) {
            throw new NotFoundException("Source account not found");
        }

        Account targetAccount = accountRepository.findByAccountNumber(targetAccountNumber);
        if (targetAccount == null) {
            throw new NotFoundException("Target account not found");
        }

        validatePin(sourceAccountNumber, pinCode);

        BigDecimal sourceBalance = sourceAccount.getBalance();
        if (sourceBalance.compareTo(amount) < 0) {
            throw new InsufficientBalanceException("Insufficient balance");
        }

        BigDecimal newSourceBalance = sourceBalance.subtract(amount);
        sourceAccount.setBalance(newSourceBalance);
        accountRepository.save(sourceAccount);

        BigDecimal targetBalance = targetAccount.getBalance();
        BigDecimal newTargetBalance = targetBalance.add(amount);
        targetAccount.setBalance(newTargetBalance);
        accountRepository.save(targetAccount);

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setTransaction_type("Fund Transfer");
        transaction.setTransaction_date(new Date());
        transaction.setSourceAccount(sourceAccount);
        transaction.setTargetAccount(targetAccount);
        transactionRepository.save(transaction);

    }
    @Override
    public List<TransactionDTO> getAllTransactionsByAccountNumber(String accountNumber) {
        List<Transaction> transactions = transactionRepository.findBySourceAccount_AccountNumberOrTargetAccount_AccountNumber(accountNumber, accountNumber);

        List<TransactionDTO> transactionDTOs = transactions.stream()
                .map(transactionMapper::toDto)
                .sorted((t1, t2) -> t2.getTransaction_date().compareTo(t1.getTransaction_date()))
                .collect(Collectors.toList());

        return transactionDTOs;
    }
}
