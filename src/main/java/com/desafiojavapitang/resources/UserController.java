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

    @GetMapping("/api/users")
    ResponseEntity<Page<UserResponse>> findAllPaged(Pageable pageable);

    @PostMapping("/api/users")
    ResponseEntity<UserResponse> create(@RequestBody UserRequest userRequest);

    @GetMapping(value = "/api/users/{id}")
    ResponseEntity<UserResponse> findById(@PathVariable Long id);

    @DeleteMapping(value = "/api/users/{id}")
    ResponseEntity<Void> delete(@PathVariable Long id);

    @PutMapping(value = "/api/users/{id}")
    ResponseEntity<UserResponse> update(@PathVariable Long id, @RequestBody UserRequest userRequest);

    @GetMapping(value = "/api/me")
    ResponseEntity<UserResponse> findAuthenticateUser(@RequestHeader("Authorization") String token);
}
