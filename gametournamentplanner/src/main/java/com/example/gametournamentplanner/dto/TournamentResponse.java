package com.example.gametournamentplanner.dto;

public record TournamentResponse(
        Long id,
        String name,
        Long gameId,
        String rules,
        Long maxParticipants
) {
}
