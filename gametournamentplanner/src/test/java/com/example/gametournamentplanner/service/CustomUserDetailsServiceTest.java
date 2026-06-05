package com.example.gametournamentplanner.service;

import com.example.gametournamentplanner.model.Account;
import com.example.gametournamentplanner.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceTest {

    @Mock
    private AccountRepository repo;

    @InjectMocks
    private CustomUserDetailsService service;

    @Test
    void ShouldLoadUserByUsername() {

        // Arrange
        Account account = new Account();
        account.setName("JPinkman");
        account.setPassword("hashedPassword");

        when(repo.findByName("JPinkman"))
                .thenReturn(Optional.of(account));

        // Act
        UserDetails user =
                service.loadUserByUsername("JPinkman");

        // Assert
        assertEquals("JPinkman", user.getUsername());
        assertEquals("hashedPassword", user.getPassword());

        assertTrue(
                user.getAuthorities()
                        .stream()
                        .anyMatch(a ->
                                a.getAuthority()
                                        .equals("ROLE_USER"))
        );
    }

    @Test
    void ShouldThrowWhenUserNotFound() {

        // Arrange
        when(repo.findByName("Unknown"))
                .thenReturn(Optional.empty());

        // Act & Assert
        UsernameNotFoundException exception =
                assertThrows(
                        UsernameNotFoundException.class,
                        () -> service.loadUserByUsername("Unknown")
                );

        assertEquals(
                "User not found",
                exception.getMessage()
        );
    }
}