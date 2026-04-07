package com.example.gametournamentplanner.controller;

import com.example.gametournamentplanner.model.Tournament;
import com.example.gametournamentplanner.service.TournamentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tournaments")
@CrossOrigin(origins = "*")
public class TournamentController {

    private final TournamentService service;

    public TournamentController(TournamentService service) {
        this.service = service;
    }

    @GetMapping
    public List<Tournament> getAll() {
        return service.getAllTournaments();
    }

    @PostMapping
    public Tournament create(@RequestBody Tournament t) {
        return service.createTournament(t);
    }
}