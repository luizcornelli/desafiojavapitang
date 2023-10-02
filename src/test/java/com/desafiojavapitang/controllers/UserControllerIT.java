package com.desafiojavapitang.controllers;


import com.desafiojavapitang.dto.CarRequest;
import com.desafiojavapitang.dto.SigninRequest;
import com.desafiojavapitang.dto.UserRequest;
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

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

    private String existsUserLogin;
    private String existsUserPassword;
    private String existsUserFirstName;
    private String existsUserLastName;
    private String existsUserEmail;
    private String existsUserBirthday;
    private String existsUserPhone;
    private Integer existsYear;
    private String existsLicensePlate;
    private String existstModel;
    private String existsColor;
    private Long existsUserId;

    @BeforeEach
    void setUp() throws Exception {

        existsUserId = 1L;

        existsUserLogin = "bob_king3242";
        existsUserPassword = "123456";

        existsUserFirstName = "Bob";
        existsUserLastName = "Falcon";
        existsUserEmail = "bob@gmail.com";
        existsUserBirthday = "2005-12-12";
        existsUserPhone = "+558123943232";

        existsYear = 2018;
        existsLicensePlate = "PDV-0625" ;
        existstModel = "Audi";
        existsColor = "White";
    }

    @Test
    public void findByUserAndAccessTokenJWT() throws Exception {

        SigninRequest signinRequest = new SigninRequest(existsUserLogin, existsUserPassword);

        String jsonBody = objectMapper.writeValueAsString(signinRequest);

        ResultActions result =
                mockMvc.perform(post("/users/api/signin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.firstName").value(existsUserFirstName));
        result.andExpect(jsonPath("$.lastName").value(existsUserLastName));
        result.andExpect(jsonPath("$.email").value(existsUserEmail));
        result.andExpect(jsonPath("$.birthday").value(existsUserBirthday));
        result.andExpect(jsonPath("$.phone").value(existsUserPhone));

        result.andExpect(jsonPath("$.cars[0].year").value(existsYear));
        result.andExpect(jsonPath("$.cars[0].licensePlate").value(existsLicensePlate));
        result.andExpect(jsonPath("$.cars[0].model").value(existstModel));
        result.andExpect(jsonPath("$.cars[0].color").value(existsColor));

        result.andExpect(jsonPath("$.accessToken", notNullValue()));
    }

    @Test
    public void findByUserPaged() throws Exception {

        ResultActions result =
                mockMvc.perform(get("/users")
                        .contentType(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());

        result.andExpect(jsonPath("$.content[0].firstName").value(existsUserFirstName));
        result.andExpect(jsonPath("$.content[0].lastName").value(existsUserLastName));
        result.andExpect(jsonPath("$.content[0].email").value(existsUserEmail));
        result.andExpect(jsonPath("$.content[0].birthday").value(existsUserBirthday));
        result.andExpect(jsonPath("$.content[0].phone").value(existsUserPhone));

        result.andExpect(jsonPath("$.content[0].cars[0].year").value(existsYear));
        result.andExpect(jsonPath("$.content[0].cars[0].licensePlate").value(existsLicensePlate));
        result.andExpect(jsonPath("$.content[0].cars[0].model").value(existstModel));
        result.andExpect(jsonPath("$.content[0].cars[0].color").value(existsColor));
    }

    @Test
    public void createUser() throws Exception {

        String dateString = "1990-05-01";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date birthday = dateFormat.parse(dateString);

        String login = "lola_2132";
        String password = "123456";
        String firstName = "Lola";
        String lastName = "Silva";
        String email = "lola@gmail.com";
        String phone = "+558123933333";

        UserRequest userRequest = new UserRequest(firstName, lastName , email,
                birthday, login, password, phone);

       Integer year = 2018;
       String licensePlate = "UDP-0232" ;
       String model = "Ferrari";
       String color = "Red";

        CarRequest carRequest = new CarRequest(year, licensePlate, model, color);

        userRequest.getCars().add(carRequest);

        String jsonBody = objectMapper.writeValueAsString(userRequest);

        ResultActions result =
                mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.firstName").value(firstName));
        result.andExpect(jsonPath("$.lastName").value(lastName));
        result.andExpect(jsonPath("$.email").value(email));
        result.andExpect(jsonPath("$.birthday").value(dateString));
        result.andExpect(jsonPath("$.phone").value(phone));

        result.andExpect(jsonPath("$.cars[0].year").value(year));
        result.andExpect(jsonPath("$.cars[0].licensePlate").value(licensePlate));
        result.andExpect(jsonPath("$.cars[0].model").value(model));
        result.andExpect(jsonPath("$.cars[0].color").value(color));
    }

    @Test
    public void findByIdUser() throws Exception {

        ResultActions result =
                mockMvc.perform(get("/users/{id}", existsUserId)
                        .contentType(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.firstName").value(existsUserFirstName));
        result.andExpect(jsonPath("$.lastName").value(existsUserLastName));
        result.andExpect(jsonPath("$.email").value(existsUserEmail));
        result.andExpect(jsonPath("$.birthday").value(existsUserBirthday));
        result.andExpect(jsonPath("$.phone").value(existsUserPhone));

        result.andExpect(jsonPath("$.cars[0].year").value(existsYear));
        result.andExpect(jsonPath("$.cars[0].licensePlate").value(existsLicensePlate));
        result.andExpect(jsonPath("$.cars[0].model").value(existstModel));
        result.andExpect(jsonPath("$.cars[0].color").value(existsColor));
    }
}
