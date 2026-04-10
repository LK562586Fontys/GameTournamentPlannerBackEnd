package com.example.gametournamentplanner.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class TournamentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldCreateTournament() throws Exception {
        //arrange
        String json = """
        {
            "name": "Test Tournament",
            "gameId": 1,
            "rules": "Some rules",
            "maxParticipants": 16
        }
        """;
        //act & assert
        mockMvc.perform(post("/api/tournaments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.name").value("Test Tournament"))
                        .andExpect(jsonPath("$.gameId").value(1));
    }

    @Test
    void shouldGetAllTournaments() throws Exception {
        //arrange
        String json = """
        {
            "name": "Test Tournament"
            "maxParticipants": 16L
        }
        """;
        //act & assert
        mockMvc.perform(post("/api/tournaments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json));

        mockMvc.perform(get("/api/tournaments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }
}