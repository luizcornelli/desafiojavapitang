package com.desafiojavapitang.services;

import com.desafiojavapitang.dto.*;
import com.desafiojavapitang.entities.UserEntity;
import com.desafiojavapitang.repositories.UserRepository;
import com.desafiojavapitang.services.mappers.Mapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.IOException;
import java.time.Instant;
import java.util.Collections;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    private UserRepository repository;
    private CarService carService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private Mapper<UserEntity, UserResponse> userEntityToUserResponseMapper;
    private Mapper<UserEntity, UserResponseCreate> userEntityToUserResponseCreateMapper;
    private Mapper<UserRequest, UserEntity> userRequestToUserEntityMapper;
    private Mapper<UserEntity, SigninResponse> userEntityToSigninResponseMapper;
    private KeycloakUserServiceImpl keycloakUserService;

    private UserServiceImpl userService;

    @BeforeEach
    public void setUp() {

        repository = mock(UserRepository.class);
        carService = mock(CarService.class);
        bCryptPasswordEncoder = mock(BCryptPasswordEncoder.class);
        userEntityToUserResponseMapper = mock(Mapper.class);
        userEntityToUserResponseCreateMapper = mock(Mapper.class);
        userRequestToUserEntityMapper = mock(Mapper.class);
        userEntityToSigninResponseMapper = mock(Mapper.class);
        keycloakUserService = mock(KeycloakUserServiceImpl.class);

        userService = new UserServiceImpl(
                repository,
                carService,
                bCryptPasswordEncoder,
                userEntityToUserResponseMapper,
                userEntityToUserResponseCreateMapper,
                userRequestToUserEntityMapper,
                userEntityToSigninResponseMapper,
                keycloakUserService
        );
    }

    @Test
    public void testAuthenticateUser_Success() throws IOException {

        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setLogin("joao.silva");
        userEntity.setPassword("senhaCriptografada");
        userEntity.setFirstName("João");
        userEntity.setLastName("Silva");
        userEntity.setEmail("joao.silva@example.com");
        userEntity.setBirthday(new Date());
        userEntity.setPhone("11999999999");
        userEntity.setLastLogin(Instant.now());

        SigninRequest signinRequest = new SigninRequest();
        signinRequest.setLogin("joao.silva");
        signinRequest.setPassword("senha123");

        SigninResponse signinResponse = new SigninResponse();
        signinResponse.setId(1L);
        signinResponse.setFirstName("João");
        signinResponse.setLastName("Silva");
        signinResponse.setEmail("joao.silva@example.com");
        signinResponse.setBirthday("1990-01-01");
        signinResponse.setPhone("11999999999");
        signinResponse.setLastLogin(Instant.now().toString());
        signinResponse.setCreatedAt(Instant.now().toString());
        signinResponse.setAccessToken("tokenJWT");

        when(repository.findByLogin("joao.silva")).thenReturn(userEntity);
        when(bCryptPasswordEncoder.matches("senha123", "senhaCriptografada")).thenReturn(true);
        when(keycloakUserService.obterTokenUsuario("joao.silva", "senha123")).thenReturn("tokenJWT");
        when(repository.save(userEntity)).thenReturn(userEntity);
        when(userEntityToSigninResponseMapper.map(userEntity)).thenReturn(signinResponse);

        SigninResponse response = userService.authenticateUser(signinRequest);

        assertNotNull(response);
        assertEquals("tokenJWT", response.getAccessToken());
        assertEquals("João", response.getFirstName());
        assertEquals("Silva", response.getLastName());

        verify(repository, times(1)).save(userEntity);
    }


    @Test
    public void testFindAllPaged() {

        Pageable pageable = PageRequest.of(0, 10);

        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setLogin("joao.silva");
        userEntity.setFirstName("João");
        userEntity.setLastName("Silva");
        userEntity.setEmail("joao.silva@example.com");
        userEntity.setBirthday(new Date());
        userEntity.setPhone("11999999999");
        userEntity.setLastLogin(Instant.now());

        UserResponse userResponse = new UserResponse();
        userResponse.setId(1L);
        userResponse.setFirstName("João");
        userResponse.setLastName("Silva");
        userResponse.setEmail("joao.silva@example.com");
        userResponse.setBirthday("1990-01-01");
        userResponse.setPhone("11999999999");
        userResponse.setLastLogin(Instant.now().toString());
        userResponse.setCreatedAt(Instant.now().toString());

        Page<UserEntity> userEntityPage = new PageImpl<>(Collections.singletonList(userEntity), pageable, 1);

        when(repository.findAll(pageable)).thenReturn(userEntityPage);

        when(userEntityToUserResponseMapper.map(userEntity)).thenReturn(userResponse);

        Page<UserResponse> result = userService.findAllPaged(pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(userResponse, result.getContent().get(0));

        verify(repository, times(1)).findAll(pageable);

        verify(userEntityToUserResponseMapper, times(1)).map(userEntity);
    }
}
