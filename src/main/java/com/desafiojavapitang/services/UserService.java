package com.desafiojavapitang.services;

import com.desafiojavapitang.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;

public interface UserService {

    SigninResponse authenticateUser(SigninRequest signinRequest);

    Page<UserResponse> findAllPaged(Pageable pageable);

    UserResponseCreate create(UserRequest userRequest);

    UserResponse findById(Long id);

    void delete(Long id);

    UserResponse update(Long id, UserRequest userRequest);

    UserResponse findAuthenticateUser(String token);

    void validateAtributtes(UserRequest userRequest);

    void deleteAll();
}
