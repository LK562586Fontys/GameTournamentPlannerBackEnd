package com.example.gametournamentplanner.dto;

public record LoginRequest(
        Long id,
        String emailAddress,
        String password
) {
}
