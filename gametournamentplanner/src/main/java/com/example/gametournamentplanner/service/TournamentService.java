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

    public List<Tournament> getAll() {
        return repo.findAll();
    }

    public Tournament create(Tournament t) {
        return repo.save(t);
    }
}