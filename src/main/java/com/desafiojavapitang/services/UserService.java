package com.desafiojavapitang.services;

import com.desafiojavapitang.dto.SigninRequest;
import com.desafiojavapitang.dto.SigninResponse;
import com.desafiojavapitang.dto.UserRequest;
import com.desafiojavapitang.dto.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    SigninResponse authenticateUser(SigninRequest signinRequest);

    Page<UserResponse> findAllPaged(Pageable pageable);

    UserResponse create(UserRequest userRequest);

    UserResponse findById(Long id);
}
