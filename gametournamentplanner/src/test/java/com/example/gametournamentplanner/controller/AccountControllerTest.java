package com.example.gametournamentplanner.controller;


import jakarta.servlet.ServletException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.security.test.context.support.WithMockUser;

import static org.junit.jupiter.api.Assertions.assertThrows;
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

    @WithMockUser
    @Test
    void ShouldntCreateAccount_AccountAlreadyExists() throws Exception {

        String json =
                """
                {
                    "name": "HSchrader",
                    "emailAddress": "hschrader@hotmail.com",
                    "password": "HSchrader2$"
                }
                """;

        mockMvc.perform(post("/api/accounts")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json));

        assertThrows(ServletException.class, () ->
                mockMvc.perform(post("/api/accounts")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
        );
    }

    @Test
    @WithMockUser
    void ShouldLogin() throws Exception {

        String json =
                """
                {
                    "name":"MikeEhrmantrout",
                    "emailAddress": "m.ehrmantrout@gmail.com",
                    "password": "Password123!"
                }
                """;

        mockMvc.perform(post("/api/accounts")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json));

        mockMvc.perform(post("/api/accounts/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists())
                .andExpect(jsonPath("$.emailAddress")
                        .value("m.ehrmantrout@gmail.com"));
    }
    @Test
    @WithMockUser
    void ShouldGetAllUsers() throws Exception {

        String json = """
                {
                    "name":"GFring",
                    "emailAddress": "g.fring@gmail.com"
                    "password": "password123!"
                }
                """;
        mockMvc.perform(post("/api/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json));

        mockMvc.perform(get("/api/accounts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }
    @Test
    @WithMockUser
    void ShouldUpdateProfile() throws Exception {

        String createJson =
                """
                {
                    "name":"Jesse",
                    "emailAddress":"jpinky@gmail.com",
                    "password":"Password123!"
                }
                """;

        mockMvc.perform(post("/api/accounts")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(createJson));

        String updateJson =
                """
                {
                    "name":"Jesse",
                    "pronouns":"he/him",
                    "country":"United States of America",
                    "biography":"Science, Bitch!"
                }
                """;

        mockMvc.perform(put("/api/accounts/1/profile")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Jesse"))
                .andExpect(jsonPath("$.pronouns").value("he/him"))
                .andExpect(jsonPath("$.country").value("United States of America"))
                .andExpect(jsonPath("$.biography").value("Science, Bitch!"));
    }
    @Test
    @WithMockUser
    void ShouldUpdateEmail() throws Exception {

        String createJson =
                """
                {
                    "name":"Walter",
                    "emailAddress":"old@gmail.com",
                    "password":"Password123!"
                }
                """;

        mockMvc.perform(post("/api/accounts")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(createJson));

        String updateJson =
                """
                {
                    "emailAddress":"new@gmail.com"
                }
                """;

        mockMvc.perform(put("/api/accounts/1/email")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.emailAddress")
                        .value("new@gmail.com"));
    }
}
