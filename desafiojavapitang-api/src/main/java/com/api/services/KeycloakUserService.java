package com.api.services;

import com.api.dto.UserRequest;

import java.io.IOException;
import java.util.Map;

public interface KeycloakUserService {

    void criarUsuarioNoKeycloak(UserRequest userRequest) throws IOException;

    String obterAccessToken() throws IOException;

    void criarUsuario(String accessToken, String username, String password, String email,
                      String firstName, String lastName) throws IOException;

    String mapToFormUrlEncoded(Map<String, String> params);

    String obterTokenUsuario(String username, String password) throws IOException;

    String buscarUsuarioId(String accessToken, String email) throws IOException;

    void deletarUsuario(String email) throws IOException;

    void atualizarUsuario(String email, UserRequest userRequest) throws IOException;
}
