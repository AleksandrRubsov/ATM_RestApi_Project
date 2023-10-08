package ru.ruba.ATM_project.service;

import ru.ruba.ATM_project.dto.TransactionDTO;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionService {
    void cashDeposit(String accountNumber, String pinCode, BigDecimal amount);
    void cashWithdrawal(String accountNumber, String pinCode, BigDecimal amount);
    void fundTransfer(String sourceAccountNumber, String targetAccountNumber, String pinCode, BigDecimal amount);
    List<TransactionDTO> getAllTransactionsByAccountNumber(String accountNumber);
}
