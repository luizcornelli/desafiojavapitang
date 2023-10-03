package com.desafiojavapitang.controllers;


import com.desafiojavapitang.utils.TokenUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class CarControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TokenUtil tokenUtil;
    
    private Long existsCarId;
    private String existsCarYear;
    private String existsCarLicensePlate;
    private String existsCarModel;
    private String existsColor;
    private String existsUserLogin; 
    private String existsUserPassword; 
    @BeforeEach
    void setUp() throws Exception {

        existsUserLogin = "bob_king3242";
        existsUserPassword = "123456";
        
        existsCarId = 1L;
        existsCarYear = "2018";
        existsCarLicensePlate = "PDV-0625";
        existsCarModel = "Audi";
        existsColor = "White";
    }

    @Test
    public void findByCarPaged() throws Exception {

        String resultString = tokenUtil.obtainAccessToken(mockMvc, existsUserLogin, existsUserPassword);

        JacksonJsonParser jsonParser = new JacksonJsonParser();
        String accessToken = jsonParser.parseMap(resultString).get("accessToken").toString();

        ResultActions result =
                mockMvc.perform(get("/api/cars")
                        .header("Authorization", accessToken)
                        .contentType(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());

        result.andExpect(jsonPath("$[0].id").value(existsCarId));
        result.andExpect(jsonPath("$[0].year").value(existsCarYear));
        result.andExpect(jsonPath("$[0].licensePlate").value(existsCarLicensePlate));
        result.andExpect(jsonPath("$[0].model").value(existsCarModel));
        result.andExpect(jsonPath("$[0].color").value(existsColor));
    }

    @Test
    public void findByIdCar() throws Exception {

        String resultString = tokenUtil.obtainAccessToken(mockMvc, existsUserLogin, existsUserPassword);

        JacksonJsonParser jsonParser = new JacksonJsonParser();
        String accessToken = jsonParser.parseMap(resultString).get("accessToken").toString();

        ResultActions result =
                mockMvc.perform(get("/api/cars/{id}", existsCarId)
                        .header("Authorization", accessToken)
                        .contentType(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());

        result.andExpect(jsonPath("$.id").value(existsCarId));
        result.andExpect(jsonPath("$.year").value(existsCarYear));
        result.andExpect(jsonPath("$.licensePlate").value(existsCarLicensePlate));
        result.andExpect(jsonPath("$.model").value(existsCarModel));
        result.andExpect(jsonPath("$.color").value(existsColor));
    }
}
