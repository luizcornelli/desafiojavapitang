package com.desafiojavapitang.resources;

import com.desafiojavapitang.dto.SigninRequest;
import com.desafiojavapitang.dto.SigninResponse;
import com.desafiojavapitang.dto.UserRequest;
import com.desafiojavapitang.dto.UserResponse;
import com.desafiojavapitang.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserControllerImpl implements UserController{

    @Autowired
    private UserService userService;

    @Override
    public ResponseEntity<SigninResponse> authenticateUser(SigninRequest signinRequest){
        return ResponseEntity.ok(userService.authenticateUser(signinRequest));
    }

    @Override
    public ResponseEntity<Page<UserResponse>> findAllPaged(Pageable pageable) {
        return ResponseEntity.ok(userService.findAllPaged(pageable));
    }

    @Override
    public ResponseEntity<UserResponse> create(UserRequest userRequest) {
        return  ResponseEntity.ok(userService.create(userRequest));
    }
}
