package com.desafiojavapitang.resources;

import com.desafiojavapitang.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "users")
public interface UserController {

    @Operation(summary = "Login do usuário", method = "POST")
    @PostMapping(value = "/api/signin")
    ResponseEntity<SigninResponse> authenticateUser(@RequestBody SigninRequest signinRequest);

    @Operation(summary = "Consulta todos os usuários páginado", method = "GET")
    @GetMapping("/api/users")
    ResponseEntity<Page<UserResponse>> findAllPaged(Pageable pageable);

    @Operation(summary = "Registra um usuário", method = "POST")
    @PostMapping("/api/users")
    ResponseEntity<UserResponseCreate> create(@RequestBody UserRequest userRequest);

    @Operation(summary = "Consulta um usuário", method = "GET")
    @GetMapping(value = "/api/users/{id}")
    ResponseEntity<UserResponse> findById(@PathVariable Long id);

    @Operation(summary = "Remove um usuário", method = "DELETE")
    @DeleteMapping(value = "/api/users/{id}")
    ResponseEntity<Void> delete(@PathVariable Long id);

    @Operation(summary = "Modifica um usuário", method = "PUT")
    @PutMapping(value = "/api/users/{id}")
    ResponseEntity<UserResponse> update(@PathVariable Long id, @RequestBody UserRequest userRequest);

    @Operation(summary = "Recupera informações do usuário logado", method = "GET")
    @GetMapping(value = "/api/me")
    ResponseEntity<UserResponse> findAuthenticateUser(@RequestHeader("Authorization") String token);
}
