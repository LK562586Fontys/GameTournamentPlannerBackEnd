package com.example.gametournamentplanner.dto;

public record CreateTournamentRequest(
        String name,
        Long gameId,
        String rules,
        Long maxParticipants
) {
}
