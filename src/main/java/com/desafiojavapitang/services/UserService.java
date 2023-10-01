package com.desafiojavapitang.services;

import com.desafiojavapitang.dto.SigninRequest;
import com.desafiojavapitang.dto.SigninResponse;

public interface UserService {
    SigninResponse authenticateUser(SigninRequest signinRequest);
}
