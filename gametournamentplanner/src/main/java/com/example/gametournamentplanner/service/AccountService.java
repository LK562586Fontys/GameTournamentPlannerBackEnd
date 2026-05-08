package com.example.gametournamentplanner.service;

import com.example.gametournamentplanner.model.Account;
import com.example.gametournamentplanner.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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
            throw new IllegalArgumentException("Duplicate Username");
        }


        if (repo.existsByEmailAddressIgnoreCase(a.getEmailAddress())) {
            throw new IllegalArgumentException("Duplicate email address");
        }

        a.setPassword(
                passwordService.hashPassword(a.getPassword())
        );
        return repo.save(a);
    }
}
