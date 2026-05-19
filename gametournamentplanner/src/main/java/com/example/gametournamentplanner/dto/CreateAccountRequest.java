package com.example.gametournamentplanner.dto;

public record CreateAccountRequest(
        String name,
        String emailAddress,
        String password
) {
}
