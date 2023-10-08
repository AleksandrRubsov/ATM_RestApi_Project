package ru.ruba.ATM_project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ruba.ATM_project.dto.AccountDTO;
import ru.ruba.ATM_project.models.Account;
import ru.ruba.ATM_project.service.AccountService;


import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/accounts")
public class AccountController {
    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;

    }

    @PostMapping("/create")
    public ResponseEntity<Account> createAccount(@RequestBody Map<String, String> request) {
        String name = request.get("name");
        String pinCode = request.get("pinCode");
        Account account = accountService.createAccount(name, pinCode);
        if (account != null) {
            return new ResponseEntity<>(account, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping()
    public ResponseEntity<List<AccountDTO>> getAllAccounts() {
        List<AccountDTO> accounts = accountService.getAllAccounts();
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }
}
