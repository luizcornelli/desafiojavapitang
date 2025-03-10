package com.desafiojavapitang.services;

import com.desafiojavapitang.dto.UserRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class KeycloakUserServiceImpl implements KeycloakUserService{

    @Value("${keycloak.auth-server-url}")
    private String authServerUrl;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.resource}")
    private String clientId;

    @Value("${keycloak.credentials.secret}")
    private String clientSecret;

    @Override
    public void criarUsuarioNoKeycloak(UserRequest userRequest) throws IOException {
        String accessToken = obterAccessToken();
        criarUsuario(accessToken, userRequest.getLogin(), userRequest.getPassword(), userRequest.getEmail(),
                userRequest.getFirstName(), userRequest.getLastName());
    }

    @Override
    public String obterAccessToken() throws IOException {
        String tokenUrl = authServerUrl + "/realms/" + realm + "/protocol/openid-connect/token";

        Map<String, String> params = new HashMap<>();
        params.put("grant_type", "client_credentials");
        params.put("client_id", clientId);
        params.put("client_secret", clientSecret);

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(tokenUrl);
            httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
            httpPost.setEntity(new StringEntity(mapToFormUrlEncoded(params)));

            HttpResponse response = httpClient.execute(httpPost);
            Map<String, Object> responseMap = new ObjectMapper().readValue(response.getEntity().getContent(), Map.class);
            return (String) responseMap.get("access_token");
        }
    }

    @Override
    public void criarUsuario(String accessToken, String username, String password, String email,
                              String firstName, String lastName) throws IOException {
        String createUserUrl = authServerUrl + "/admin/realms/" + realm + "/users";

        Map<String, Object> user = new HashMap<>();
        user.put("username", username);
        user.put("email", email);
        user.put("firstName", firstName);
        user.put("lastName", lastName);
        user.put("enabled", true);

        Map<String, Object> credentials = new HashMap<>();
        credentials.put("type", "password");
        credentials.put("value", password);
        credentials.put("temporary", false);

        user.put("credentials", Collections.singletonList(credentials));

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(createUserUrl);
            httpPost.setHeader("Content-Type", "application/json");
            httpPost.setHeader("Authorization", "Bearer " + accessToken);
            httpPost.setEntity(new StringEntity(new ObjectMapper().writeValueAsString(user)));

            HttpResponse response = httpClient.execute(httpPost);
            String responseBody = EntityUtils.toString(response.getEntity());
            System.out.println("Resposta do Keycloak: " + responseBody);

            if (response.getStatusLine().getStatusCode() != 201) {
                throw new RuntimeException("Erro ao criar usuário no Keycloak: " + response.getStatusLine().getReasonPhrase());
            }
        }
    }

    @Override
    public String mapToFormUrlEncoded(Map<String, String> params) {
        StringBuilder result = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (result.length() > 0) {
                result.append("&");
            }
            result.append(entry.getKey());
            result.append("=");
            result.append(entry.getValue());
        }
        return result.toString();
    }

    @Override
    public String obterTokenUsuario(String username, String password) throws IOException {
        String tokenUrl = authServerUrl + "/realms/" + realm + "/protocol/openid-connect/token";

        Map<String, String> params = new HashMap<>();
        params.put("grant_type", "password");
        params.put("client_id", clientId);
        params.put("client_secret", clientSecret);
        params.put("username", username);
        params.put("password", password);

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(tokenUrl);
            httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
            httpPost.setEntity(new StringEntity(mapToFormUrlEncoded(params)));

            HttpResponse response = httpClient.execute(httpPost);
            String responseBody = EntityUtils.toString(response.getEntity());

            Map<String, Object> responseMap = new ObjectMapper().readValue(responseBody, Map.class);
            return (String) responseMap.get("access_token");
        }
    }

}