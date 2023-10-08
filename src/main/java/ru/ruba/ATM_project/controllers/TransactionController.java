package ru.ruba.ATM_project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ruba.ATM_project.dto.AmountRequest;
import ru.ruba.ATM_project.dto.FundTransferRequest;
import ru.ruba.ATM_project.dto.TransactionDTO;
import ru.ruba.ATM_project.models.Account;
import ru.ruba.ATM_project.service.AccountService;
import ru.ruba.ATM_project.service.TransactionService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    private final AccountService accountService;

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(AccountService accountService, TransactionService transactionService) {
        this.accountService = accountService;
        this.transactionService = transactionService;
    }

    @PostMapping("/deposit")
    public ResponseEntity<?> cashDeposit(@RequestBody AmountRequest amountRequest) {
        transactionService.cashDeposit(amountRequest.getAccountNumber(), amountRequest.getPinCode(), amountRequest.getAmount());

        Map<String, String> response =  new HashMap<>();
        response.put("msg", "Cash deposited successfully");

        return new ResponseEntity<>( response, HttpStatus.OK);
    }

    @PostMapping("/withdraw")
    public ResponseEntity<?> cashWithdrawal(@RequestBody AmountRequest amountRequest) {
        transactionService.cashWithdrawal(amountRequest.getAccountNumber(), amountRequest.getPinCode(), amountRequest.getAmount());

        Map<String, String> response =  new HashMap<>();
        response.put("msg", "Cash withdrawn successfully");

        return new ResponseEntity<>( response, HttpStatus.OK);
    }

    @PostMapping("/fund-transfer")
    public ResponseEntity<?> fundTransfer(@RequestBody FundTransferRequest fundTransferRequest) {
        transactionService.fundTransfer(fundTransferRequest.getSourceAccountNumber(), fundTransferRequest.getTargetAccountNumber(), fundTransferRequest.getPinCode(), fundTransferRequest.getAmount());
        Map<String, String> response =  new HashMap<>();
        response.put("msg", "Fund transferred successfully");

        return new ResponseEntity<>( response, HttpStatus.OK);
    }

    @GetMapping("/transactions")
    public ResponseEntity<List<TransactionDTO>> getAllTransactionsByAccountNumber(String accountNumber) {
        Account currentAccount = accountService.getAccountByNumber(accountNumber);
        List<TransactionDTO> transactions = transactionService.getAllTransactionsByAccountNumber(currentAccount.getAccountNumber());
        return ResponseEntity.ok(transactions);
    }
}
