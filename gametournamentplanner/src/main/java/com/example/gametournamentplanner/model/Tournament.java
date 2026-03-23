package com.example.gametournamentplanner.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;


@Entity
public class Tournament {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    public void setName(String name) { this.name = name; }

    private Long gameId;
    public void setGameId(Long gameId) { this.gameId = gameId; }

    private String rules;
    public void setRules(String rules) { this.rules = rules; }

    private Long maxParticipants;
    public void setMaxParticipants(Long maxParticipants) { this.maxParticipants = maxParticipants; }
}
