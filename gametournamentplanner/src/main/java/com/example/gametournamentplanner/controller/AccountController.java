package com.example.gametournamentplanner.controller;

import com.example.gametournamentplanner.model.Account;
import com.example.gametournamentplanner.service.AccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@CrossOrigin(origins = "*")
public class AccountController {

    private final AccountService service;

    public AccountController(AccountService service) {
        this.service = service;
    }

    @GetMapping
    public List<Account> getAllAccounts() {
        return service.getAccounts();
    }

    @PostMapping
    public Account createAccount(@RequestBody Account a) {
        return service.createAccount(a);
    }
}
