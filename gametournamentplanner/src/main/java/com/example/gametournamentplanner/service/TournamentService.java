package com.example.gametournamentplanner.service;

import com.example.gametournamentplanner.model.Tournament;
import com.example.gametournamentplanner.repository.TournamentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TournamentService {

    private final TournamentRepository repo;

    public TournamentService(TournamentRepository repo) {
        this.repo = repo;
    }

    public List<Tournament> getAllTournaments() {
        return repo.findAll();
    }

    public Tournament createTournament(Tournament t) {
        Long max = t.getMaxParticipants();

        if (max == null || max <= 0 || (max & (max - 1)) != 0) {
            throw new IllegalArgumentException("Max participants must be a positive power of 2.");
        }

        return repo.save(t);
    }
}