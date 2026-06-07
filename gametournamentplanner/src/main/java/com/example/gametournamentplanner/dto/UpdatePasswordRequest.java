package com.example.gametournamentplanner.dto;

public record UpdatePasswordRequest(
        String currentPassword,
        String newPassword
) {}
