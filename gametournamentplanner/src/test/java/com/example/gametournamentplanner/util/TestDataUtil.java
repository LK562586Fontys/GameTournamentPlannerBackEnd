package com.example.gametournamentplanner.util;

import com.example.gametournamentplanner.model.Tournament;

public class TestDataUtil {

    public static Tournament createTournament() {
        Tournament t = new Tournament();
        t.setName("Default Tournament");
        t.setGameId(1L);
        t.setRules("Rules");
        t.setMaxParticipants(16L);
        return t;
    }
}
