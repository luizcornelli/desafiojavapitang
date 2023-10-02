package com.desafiojavapitang.resources;

import com.desafiojavapitang.dto.SigninRequest;
import com.desafiojavapitang.dto.SigninResponse;
import com.desafiojavapitang.dto.UserRequest;
import com.desafiojavapitang.dto.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/users")
public interface UserController {

    @PostMapping(value = "/api/signin")
    ResponseEntity<SigninResponse> authenticateUser(@RequestBody SigninRequest signinRequest);

    @GetMapping
    ResponseEntity<Page<UserResponse>> findAllPaged(Pageable pageable);

    @PostMapping
    ResponseEntity<UserResponse> create(@RequestBody UserRequest userRequest);

    @GetMapping(value = "/{id}")
    ResponseEntity<UserResponse> findById(@PathVariable Long id);

    @DeleteMapping(value = "/{id}")
    ResponseEntity<Void> delete(@PathVariable Long id);
}
