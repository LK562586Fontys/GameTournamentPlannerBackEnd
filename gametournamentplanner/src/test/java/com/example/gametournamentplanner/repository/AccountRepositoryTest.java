package com.example.gametournamentplanner.repository;

import com.example.gametournamentplanner.model.Account;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class AccountRepositoryTest {

    @Autowired
    private AccountRepository repository;

    @Test
    void shouldSaveTournament() {
        //arrange
        Account t = new Account();
        t.setName("Test Account");
        //act
        Account saved = repository.save(t);
        //assert
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getName()).isEqualTo("Test Account");
    }

    @Test
    void shouldFindAllAccounts() {
        //arrange
        Account t1 = new Account();
        t1.setName("T1");
        t1.setEmailAddress("Test1@gmail.com");

        Account t2 = new Account();
        t2.setName("T2");
        t2.setEmailAddress("Test2@gmail.com");

        repository.save(t1);
        repository.save(t2);
        //act
        List<Account> result = repository.findAll();
        //assert
        assertThat(result).hasSize(2);
    }

    @Test
    void ShouldntSaveAccounts_DuplicateEmailAddresses() {
        // Arrange
        Account a = new Account();
        a.setName("Joey");
        a.setEmailAddress("Joey@gmail.com");
        a.setPassword("hashedPassword");

        repository.save(a);

        // Act
        boolean exists =
                repository.existsByEmailAddressIgnoreCase("joey@gmail.com");

        // Assert
        assertTrue(exists);
    }
    @Test
    void ShouldntSaveAccounts_DuplicateUsername() {
        // Arrange
        Account a = new Account();
        a.setName("Joey");
        a.setEmailAddress("Joey@gmail.com");
        a.setPassword("hashedPassword");

        repository.save(a);

        // Act
        boolean exists =
                repository.existsByName("Joey");

        // Assert
        assertTrue(exists);
    }
    @Test
    void ShouldSaveAccounts_DuplicateUsername() {
        // Arrange
        Account a = new Account();
        a.setName("Joey");
        a.setEmailAddress("Joey@gmail.com");
        a.setPassword("hashedPassword");

        repository.save(a);

        // Act
        boolean exists =
                repository.existsByName("joey");

        // Assert
        assertFalse(exists);
    }
}
