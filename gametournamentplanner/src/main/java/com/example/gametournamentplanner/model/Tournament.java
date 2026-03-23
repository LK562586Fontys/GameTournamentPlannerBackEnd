package com.example.gametournamentplanner.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;


@Entity
public class Tournament {
    @Id
    @GeneratedValue
    private Long id;

    public Long getId() {
        return id;
    }

    private String name;
    public void setName(String name) { this.name = name; }
    public String getName() {
        return name;
    }


    private Long gameId;
    public void setGameId(Long gameId) { this.gameId = gameId; }
    public Long getGameId() {
        return gameId;
    }

    private String rules;
    public void setRules(String rules) { this.rules = rules; }

    public String getRules() {
        return rules;
    }

    private Long maxParticipants;
    public void setMaxParticipants(Long maxParticipants) { this.maxParticipants = maxParticipants; }

    public Long getMaxParticipants() {
        return maxParticipants;
    }
}
