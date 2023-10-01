package com.desafiojavapitang.controllers;


import com.desafiojavapitang.dto.SigninRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class UserControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private String login;
    private String password;

    @BeforeEach
    void setUp() throws Exception {

        login = "bob_king3242";
        password = "123456";
    }

    @Test
    public void getProfileShouldReturnSelfWhenVisitorLogged() throws Exception {

        SigninRequest signinRequest = new SigninRequest(login, password);

        String jsonBody = objectMapper.writeValueAsString(signinRequest);

        ResultActions result =
                mockMvc.perform(post("/users/api/signin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.firstName", notNullValue()));
        result.andExpect(jsonPath("$.lastName", notNullValue()));
        result.andExpect(jsonPath("$.email", notNullValue()));
        result.andExpect(jsonPath("$.birthday", notNullValue()));
        result.andExpect(jsonPath("$.phone", notNullValue()));
        result.andExpect(jsonPath("$.accessToken", notNullValue()));
    }
}
