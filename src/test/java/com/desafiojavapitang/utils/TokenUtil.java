package com.desafiojavapitang.utils;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.desafiojavapitang.dto.SigninRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Component
public class TokenUtil {

	@Autowired
	private ObjectMapper objectMapper;

	public String obtainAccessToken(MockMvc mockMvc, String existsUserLogin, String existsUserPassword) throws Exception {

		SigninRequest signinRequest = new SigninRequest(existsUserLogin, existsUserPassword);

		String jsonBody = objectMapper.writeValueAsString(signinRequest);

		ResultActions result =
				mockMvc.perform(post("/api/signin")
						.contentType(MediaType.APPLICATION_JSON)
						.content(jsonBody)
						.accept(MediaType.APPLICATION_JSON));

		return result.andReturn().getResponse().getContentAsString();
	}
}
