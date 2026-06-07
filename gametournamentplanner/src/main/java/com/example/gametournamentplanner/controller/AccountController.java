package com.example.gametournamentplanner.controller;

import com.example.gametournamentplanner.dto.*;
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
    public List<AccountResponse> getAllAccounts() {
        return service.getAccounts()
                .stream()
                .map(a -> new AccountResponse(
                        a.getId(),
                        a.getName(),
                        a.getEmailAddress()))
                .toList();
    }

    @PostMapping
    public AccountResponse createAccount(
            @RequestBody CreateAccountRequest request) {

        Account account = new Account();
        account.setName(request.name());
        account.setEmailAddress(request.emailAddress());

        account.setPassword(request.password());

        Account saved = service.createAccount(account);

        return new AccountResponse(
                saved.getId(),
                saved.getName(),
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
                "name", account.getName(),
                "emailAddress", account.getEmailAddress()
        );
    }
    @PutMapping("/{id}/password")
    public Account updatePassword(
            @PathVariable Long id,
            @RequestBody UpdatePasswordRequest request)
    {
        return service.updatePassword(id, request);
    }
    @PutMapping("/{id}/email")
    public Account updateEmail(
            @PathVariable Long id,
            @RequestBody UpdateEmailRequest request)
    {
        return service.updateEmail(id, request);
    }
    @PutMapping("/{id}/profile")
    public Account updateProfile(
            @PathVariable Long id,
            @RequestBody UpdateProfileRequest request)
    {
        return service.updateProfile(id, request);
    }
}

