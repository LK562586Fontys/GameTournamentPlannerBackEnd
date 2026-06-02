package com.example.gametournamentplanner.controller;

import com.example.gametournamentplanner.dto.AccountResponse;
import com.example.gametournamentplanner.dto.CreateAccountRequest;
import com.example.gametournamentplanner.dto.LoginRequest;
import com.example.gametournamentplanner.model.Account;
import com.example.gametournamentplanner.service.AccountService;
import org.springframework.web.bind.annotation.*;
import com.example.gametournamentplanner.service.JwtService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/accounts")
@CrossOrigin(origins = {
        "http://localhost:3000",
        "http://localhost:5173"
})
public class AccountController {

    private final AccountService service;
    private final JwtService jwtService;

    public AccountController(
            AccountService service,
            JwtService jwtService) {
        this.service = service;
        this.jwtService = jwtService;
    }

    @GetMapping
    public List<Account> getAllAccounts() {
        return service.getAccounts();
    }

    @PostMapping
    public AccountResponse createAccount(
            @RequestBody CreateAccountRequest request) {

        Account account = new Account();
        account.setNaam(request.name());
        account.setEmailAddress(request.emailAddress());

        account.setPassword(request.password());

        Account saved = service.createAccount(account);

        return new AccountResponse(
                saved.getId(),
                saved.getNaam(),
                saved.getEmailAddress()
        );
    }
    @PostMapping("/login")
    public Map<String, Object> login(
            @RequestBody LoginRequest request) {

        Account account = service.login(
                request.emailAddress(),
                request.password());

        String token = jwtService.generateToken(
                account.getEmailAddress());

        return Map.of(
                "token", token,
                "id", account.getId(),
                "name", account.getNaam(),
                "emailAddress", account.getEmailAddress()
        );
    }

}

