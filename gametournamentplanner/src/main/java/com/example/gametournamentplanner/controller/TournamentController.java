package com.example.gametournamentplanner.controller;

import com.example.gametournamentplanner.dto.TournamentResponse;
import com.example.gametournamentplanner.dto.CreateTournamentRequest;
import com.example.gametournamentplanner.model.Tournament;
import com.example.gametournamentplanner.service.TournamentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tournaments")
@CrossOrigin(origins = "http://localhost:3000")
public class TournamentController {

    private final TournamentService service;

    public TournamentController(TournamentService service) {
        this.service = service;
    }

    @GetMapping
    public List<Tournament> getAllTournaments() {
        return service.getAllTournaments();
    }

    @PostMapping
    public TournamentResponse createTournament(
            @RequestBody CreateTournamentRequest request) {

        Tournament tournament = new Tournament();
        tournament.setName(request.name());
        tournament.setGameId(request.gameId());
        tournament.setRules(request.rules());
        tournament.setMaxParticipants(request.maxParticipants());

        Tournament saved = service.createTournament(tournament);

        return new TournamentResponse(
                saved.getId(),
                saved.getName(),
                saved.getGameId(),
                saved.getRules(),
                saved.getMaxParticipants()
        );
    }
}