package com.example.gametournamentplanner.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @WithMockUser
    @Test
    void ShouldCreateAccount() throws Exception {
        //arrange
        String json =
                """
                {
                "name": "JPinkman",
                "emailAddress": "test@gmail.com",
                "password": "JPinkman2$"
                }
                """;

        mockMvc.perform(post("/api/accounts")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());

    }
    /*
    @WithMockUser
    @Test
    void ShouldntCreateAccount_RejectDuplicateEmail() throws Exception {

        String json =
                """
                {
                    "name": "HSchrader",
                    "emailAddress": "test@hotmail.com",
                    "password": "HSchrader2$"
                }
                """;

        String json2 =
                """
                {
                    "name": "WWhite",
                    "emailAddress": "test@hotmail.com",
                    "password": "WWhite2$"
                }
                """;

        mockMvc.perform(post("/api/accounts")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json));

        mockMvc.perform(post("/api/accounts")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json2))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Duplicate email address"));
    }
    */
}
