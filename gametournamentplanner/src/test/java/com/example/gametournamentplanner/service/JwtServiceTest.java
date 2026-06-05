package com.example.gametournamentplanner.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {

    private JwtService jwtService;

    @BeforeEach
    void setUp() {

        jwtService = new JwtService();

        ReflectionTestUtils.setField(
                jwtService,
                "secret",
                "mySuperSecretKeyForJwtTesting12345678901234567890"
        );
    }

    @Test
    void ShouldGenerateAndValidateToken() {

        String token =
                jwtService.generateToken(
                        "test@gmail.com");

        assertNotNull(token);

        assertTrue(
                jwtService.isTokenValid(token));
    }

    @Test
    void ShouldExtractEmailFromToken() {

        String token =
                jwtService.generateToken(
                        "test@gmail.com");

        String email =
                jwtService.extractEmail(token);

        assertEquals(
                "test@gmail.com",
                email);
    }

    @Test
    void ShouldReturnFalseForInvalidToken() {

        assertFalse(
                jwtService.isTokenValid(
                        "not-a-real-jwt"));
    }
}