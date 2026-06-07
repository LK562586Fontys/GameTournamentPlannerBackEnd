package com.example.gametournamentplanner.service;

import com.example.gametournamentplanner.dto.UpdateEmailRequest;
import com.example.gametournamentplanner.dto.UpdatePasswordRequest;
import com.example.gametournamentplanner.dto.UpdateProfileRequest;
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
    @Test
    void ShouldUpdateProfile() {
        // Arrange
        Account account = new Account();
        account.setName("Walter");
        account.setPronouns("he/him");
        account.setCountry("United States of America");
        account.setBiography("Old bio");

        UpdateProfileRequest request =
                new UpdateProfileRequest(
                        "Skyler",
                        "she/her",
                        "United States of America",
                        "New bio");

        when(repo.findById(1L))
                .thenReturn(Optional.of(account));

        when(repo.save(any(Account.class)))
                .thenReturn(account);

        // Act
        Account result = service.updateProfile(1L, request);

        // Assert
        assertEquals("Skyler", result.getName());
        assertEquals("she/her", result.getPronouns());
        assertEquals("United States of America", result.getCountry());
        assertEquals("New bio", result.getBiography());

        verify(repo, times(1)).findById(1L);
        verify(repo, times(1)).save(account);
    }
    @Test
    void ShouldUpdateEmail() {
        // Arrange
        Account account = new Account();
        account.setEmailAddress("old@email.com");

        UpdateEmailRequest request =
                new UpdateEmailRequest("new@email.com");

        when(repo.findById(1L))
                .thenReturn(Optional.of(account));

        when(repo.existsByEmailAddressIgnoreCase("new@email.com"))
                .thenReturn(false);

        when(repo.save(any(Account.class)))
                .thenReturn(account);

        // Act
        Account result = service.updateEmail(1L, request);

        // Assert
        assertEquals("new@email.com",
                result.getEmailAddress());

        verify(repo).findById(1L);
        verify(repo).existsByEmailAddressIgnoreCase("new@email.com");
        verify(repo).save(account);
    }
    @Test
    void ShouldThrowWhenEmailAlreadyExists() {
        // Arrange
        Account account = new Account();

        UpdateEmailRequest request =
                new UpdateEmailRequest("taken@email.com");

        when(repo.findById(1L))
                .thenReturn(Optional.of(account));

        when(repo.existsByEmailAddressIgnoreCase("taken@email.com"))
                .thenReturn(true);

        // Act & Assert
        assertThrows(
                IllegalArgumentException.class,
                () -> service.updateEmail(1L, request));

        verify(repo, never()).save(any(Account.class));
    }
    @Test
    void ShouldUpdatePassword() {
        // Arrange
        Account account = new Account();
        account.setPassword("oldHash");

        UpdatePasswordRequest request =
                new UpdatePasswordRequest(
                        "oldPassword",
                        "newPassword");

        when(repo.findById(1L))
                .thenReturn(Optional.of(account));

        when(passwordService.matches(
                "oldPassword",
                "oldHash"))
                .thenReturn(true);

        when(passwordService.hashPassword("newPassword"))
                .thenReturn("newHash");

        when(repo.save(any(Account.class)))
                .thenReturn(account);

        // Act
        Account result =
                service.updatePassword(1L, request);

        // Assert
        assertEquals("newHash",
                result.getPassword());

        verify(passwordService)
                .matches("oldPassword", "oldHash");

        verify(passwordService)
                .hashPassword("newPassword");

        verify(repo).save(account);
    }
    @Test
    void ShouldThrowWhenCurrentPasswordIsIncorrect() {
        // Arrange
        Account account = new Account();
        account.setPassword("storedHash");

        UpdatePasswordRequest request =
                new UpdatePasswordRequest(
                        "wrongPassword",
                        "newPassword");

        when(repo.findById(1L))
                .thenReturn(Optional.of(account));

        when(passwordService.matches(
                "wrongPassword",
                "storedHash"))
                .thenReturn(false);

        // Act & Assert
        assertThrows(
                IllegalArgumentException.class,
                () -> service.updatePassword(1L, request));

        verify(repo, never()).save(any(Account.class));
    }
}
