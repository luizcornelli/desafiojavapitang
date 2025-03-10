package com.desafiojavapitang.services;

import com.desafiojavapitang.dto.UserRequest;

import java.io.IOException;
import java.util.Map;

public interface KeycloakUserService {

    void criarUsuarioNoKeycloak(UserRequest userRequest) throws IOException;

    String obterAccessToken() throws IOException;

    void criarUsuario(String accessToken, String username, String password, String email,
                      String firstName, String lastName) throws IOException;

    String mapToFormUrlEncoded(Map<String, String> params);

    String obterTokenUsuario(String username, String password) throws IOException;
}
