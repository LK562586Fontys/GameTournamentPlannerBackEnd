package com.example.gametournamentplanner.service;

import com.example.gametournamentplanner.dto.UpdateEmailRequest;
import com.example.gametournamentplanner.dto.UpdatePasswordRequest;
import com.example.gametournamentplanner.dto.UpdateProfileRequest;
import com.example.gametournamentplanner.model.Account;
import com.example.gametournamentplanner.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    public final AccountRepository repo;
    public final PasswordService passwordService;

    public AccountService(AccountRepository repo, PasswordService passwordService) {this.repo = repo;
        this.passwordService = passwordService;
    }

    public List<Account> getAccounts() {return repo.findAll();}

    public Account createAccount(Account a) {

        if (repo.existsByName(a.getName())) {
            throw new IllegalArgumentException("Account Already Exists");
        }


        if (repo.existsByEmailAddressIgnoreCase(a.getEmailAddress())) {
            throw new IllegalArgumentException("Account Already Exists");
        }

        a.setPassword(
                passwordService.hashPassword(a.getPassword())
        );
        return repo.save(a);
    }
    public Account login(String emailAddress, String password)
    {
        Account account = repo
                .findByEmailAddress(emailAddress)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Invalid credentials"));

        boolean validPassword =
                passwordService.matches(
                        password,
                        account.getPassword());

        if (!validPassword)
        {
            throw new IllegalArgumentException(
                    "Invalid credentials");
        }

        return account;
    }
    public Optional<Account> getSpecificAccount(Long id) {
        return repo.findById(id);
    }
    public Account updateProfile(
            Long accountId,
            UpdateProfileRequest request)
    {
        Account account = repo.findById(accountId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Account not found"));

        account.setName(request.name());
        account.setPronouns(request.pronouns());
        account.setCountry(request.country());
        account.setBiography(request.biography());

        return repo.save(account);
    }
    public Account updateEmail(
            Long accountId,
            UpdateEmailRequest request)
    {
        Account account = repo.findById(accountId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Account not found"));

        if (repo.existsByEmailAddressIgnoreCase(request.emailAddress()))
        {
            throw new IllegalArgumentException("Email already in use");
        }

        account.setEmailAddress(request.emailAddress());

        return repo.save(account);
    }
    public Account updatePassword(
            Long accountId,
            UpdatePasswordRequest request)
    {
        Account account = repo.findById(accountId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Account not found"));

        if (!passwordService.matches(
                request.currentPassword(),
                account.getPassword()))
        {
            throw new IllegalArgumentException("Current password incorrect");
        }

        account.setPassword(
                passwordService.hashPassword(
                        request.newPassword()));

        return repo.save(account);
    }
}
