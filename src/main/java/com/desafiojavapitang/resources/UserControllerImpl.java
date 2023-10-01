package com.desafiojavapitang.resources;

import com.desafiojavapitang.dto.SigninRequest;
import com.desafiojavapitang.dto.SigninResponse;
import com.desafiojavapitang.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserControllerImpl implements UserController{

    @Autowired
    private UserService userService;

    @Override
    public ResponseEntity<SigninResponse> authenticateUser(SigninRequest signinRequest){
        return ResponseEntity.ok(userService.authenticateUser(signinRequest));
    }
}
