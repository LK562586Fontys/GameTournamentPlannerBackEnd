package com.example.gametournamentplanner.repository;

import com.example.gametournamentplanner.model.Tournament;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class TournamentRepositoryTest {

    @Autowired
    private TournamentRepository repository;

    @Test
    void shouldSaveTournament() {
        //arrange
        Tournament t = new Tournament();
        t.setName("Test Tournament");
        t.setMaxParticipants(16L);
        //act
        Tournament saved = repository.save(t);
        //assert
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getName()).isEqualTo("Test Tournament");
    }

    @Test
    void shouldFindAllTournaments() {
        //arrange
        Tournament t1 = new Tournament();
        t1.setName("T1");
        t1.setMaxParticipants(16L);

        Tournament t2 = new Tournament();
        t2.setName("T2");
        t2.setMaxParticipants(16L);

        repository.save(t1);
        repository.save(t2);
        //act
        List<Tournament> result = repository.findAll();
        //assert
        assertThat(result).hasSize(2);
    }
}