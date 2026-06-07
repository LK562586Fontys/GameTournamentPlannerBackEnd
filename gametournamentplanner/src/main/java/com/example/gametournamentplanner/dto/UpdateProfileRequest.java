package com.example.gametournamentplanner.dto;

public record UpdateProfileRequest(
        String name,
        String pronouns,
        String country,
        String biography
) {}
