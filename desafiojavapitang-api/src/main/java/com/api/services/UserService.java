package com.api.services;

import com.api.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
