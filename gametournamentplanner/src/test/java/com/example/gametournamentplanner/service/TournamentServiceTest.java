package com.example.gametournamentplanner.service;

import com.example.gametournamentplanner.model.Tournament;
import com.example.gametournamentplanner.repository.TournamentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TournamentServiceTest {

    @Mock
    private TournamentRepository repo;

    @InjectMocks
    private TournamentService service;

    @Test
    void shouldReturnAllTournaments() {
        when(repo.findAll()).thenReturn(List.of(new Tournament()));

        List<Tournament> result = service.getAllTournaments();

        assertEquals(1, result.size());
        verify(repo, times(1)).findAll();
    }

    @Test
    void shouldCreateTournament() {
        Tournament t = new Tournament();
        t.setName("Test");

        when(repo.save(any(Tournament.class))).thenReturn(t);

        Tournament result = service.createTournament(t);

        assertEquals("Test", result.getName());
        verify(repo, times(1)).save(t);
    }
}