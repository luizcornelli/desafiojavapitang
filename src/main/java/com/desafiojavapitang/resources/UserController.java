package com.desafiojavapitang.resources;

import com.desafiojavapitang.dto.SigninRequest;
import com.desafiojavapitang.dto.SigninResponse;
import com.desafiojavapitang.dto.UserRequest;
import com.desafiojavapitang.dto.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public interface UserController {

    @PostMapping(value = "/api/signin")
    ResponseEntity<SigninResponse> authenticateUser(@RequestBody SigninRequest signinRequest);

    @GetMapping("/users")
    ResponseEntity<Page<UserResponse>> findAllPaged(Pageable pageable);

    @PostMapping("/users")
    ResponseEntity<UserResponse> create(@RequestBody UserRequest userRequest);

    @GetMapping(value = "users/{id}")
    ResponseEntity<UserResponse> findById(@PathVariable Long id);

    @DeleteMapping(value = "users/{id}")
    ResponseEntity<Void> delete(@PathVariable Long id);

    @PutMapping(value = "users/{id}")
    ResponseEntity<UserResponse> update(@PathVariable Long id, @RequestBody UserRequest userRequest);

    @GetMapping(value = "/api/me")
    ResponseEntity<UserResponse> findAuthenticateUser(@RequestHeader("Authorization") String token);
}
