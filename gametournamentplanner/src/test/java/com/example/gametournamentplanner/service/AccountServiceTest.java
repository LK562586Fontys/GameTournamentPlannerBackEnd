package com.example.gametournamentplanner.service;

import com.example.gametournamentplanner.model.Account;
import com.example.gametournamentplanner.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {
    @Mock
    private AccountRepository repo;

    @Mock
    private PasswordService passwordService;

    @InjectMocks
    private AccountService service;

    @Test
    void ShouldReturnAllAccounts() {
        //arrange
        when(repo.findAll()).thenReturn(List.of(new Account()));
        //act
        List<Account> result = service.getAccounts();
        //assert
        assertEquals(1, result.size());
        verify(repo, times(1)).findAll();
    }

    @Test
    void ShouldCreateAccount() {
        //arrange
        Account a = new Account();
        a.setName("Jesse");
        a.setEmailAddress("JPinkman@gmail.com");
        a.setPassword("mypassword2$");

        when(passwordService.hashPassword(any(String.class)))
                .thenReturn("hashedPassword");

        when(repo.save(any(Account.class))).thenReturn(a);
        //act
        Account result = service.createAccount(a);
        //Assert
        assertEquals("Jesse", result.getName());
        verify(repo, times(1)).save(a);
    }

    @Test
    void ShouldntCreateAccount_DuplicateEmailAddress() {
        // Arrange
        Account a = new Account();
        a.setName("Joey");
        a.setEmailAddress("joey@gmail.com");
        a.setPassword("password123");

        when(repo.existsByEmailAddressIgnoreCase("joey@gmail.com"))
                .thenReturn(true);

        // Act + Assert
        IllegalArgumentException ex =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> service.createAccount(a)
                );

        assertEquals("Account Already Exists", ex.getMessage());

        verify(repo, never()).save(any(Account.class));
    }
    @Test
    void ShouldntCreateAccount_DuplicateUsername() {
        // Arrange
        Account a = new Account();
        a.setName("Joey");
        a.setEmailAddress("joey@gmail.com");
        a.setPassword("password123");

        when(repo.existsByName("Joey"))
                .thenReturn(true);

        // Act + Assert
        IllegalArgumentException ex =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> service.createAccount(a)
                );

        assertEquals("Account Already Exists", ex.getMessage());

        verify(repo, never()).save(any(Account.class));
    }
    @Test
    void login_ShouldReturnAccount_WhenCredentialsAreValid() {
        Account account = new Account();
        account.setEmailAddress("test@test.com");
        account.setPassword("hashedPassword");

        when(repo.findByEmailAddress("test@test.com"))
                .thenReturn(Optional.of(account));

        when(passwordService.matches(
                "password123",
                "hashedPassword"))
                .thenReturn(true);

        Account result = service.login(
                "test@test.com",
                "password123");

        assertEquals(account, result);
    }

    @Test
    void login_ShouldThrowException_WhenAccountNotFound() {

        when(repo.findByEmailAddress("missing@test.com"))
                .thenReturn(Optional.empty());

        assertThrows(
                IllegalArgumentException.class,
                () -> service.login(
                        "missing@test.com",
                        "password123"));
    }

    @Test
    void login_ShouldThrowException_WhenPasswordIsWrong() {

        Account account = new Account();
        account.setPassword("hashedPassword");

        when(repo.findByEmailAddress("test@test.com"))
                .thenReturn(Optional.of(account));

        when(passwordService.matches(
                "wrongPassword",
                "hashedPassword"))
                .thenReturn(false);

        assertThrows(
                IllegalArgumentException.class,
                () -> service.login(
                        "test@test.com",
                        "wrongPassword"));
    }
}
