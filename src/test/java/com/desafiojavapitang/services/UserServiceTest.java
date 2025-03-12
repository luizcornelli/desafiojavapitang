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
import java.util.Optional;

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
    public void testFindAllPaged_Success() {

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

    @Test
    void testCreateUser_Success() throws IOException {

        UserRequest userRequest = new UserRequest();
        userRequest.setFirstName("John");
        userRequest.setLastName("Doe");
        userRequest.setEmail("john.doe@example.com");
        userRequest.setBirthday(new Date());
        userRequest.setLogin("johndoe");
        userRequest.setPassword("securePassword");
        userRequest.setPhone("123456789");
        userRequest.getCars().addAll(Collections.emptyList());

        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setFirstName(userRequest.getFirstName());
        userEntity.setLastName(userRequest.getLastName());
        userEntity.setEmail(userRequest.getEmail());
        userEntity.setBirthday(userRequest.getBirthday());
        userEntity.setLogin(userRequest.getLogin());
        userEntity.setPassword("encodedPassword");
        userEntity.setPhone(userRequest.getPhone());

        UserResponseCreate expectedResponse = new UserResponseCreate(1L, "John", "Doe", "john.doe@example.com", "2000-01-01", "123456789", "2025-01-01", null);

        when(repository.existsByEmail(userRequest.getEmail())).thenReturn(false);
        when(repository.existsByLogin(userRequest.getLogin())).thenReturn(false);
        when(bCryptPasswordEncoder.encode(userRequest.getPassword())).thenReturn("encodedPassword");
        when(userRequestToUserEntityMapper.map(userRequest)).thenReturn(userEntity);
        when(repository.save(userEntity)).thenReturn(userEntity);
        when(userEntityToUserResponseCreateMapper.map(userEntity)).thenReturn(expectedResponse);

        UserResponseCreate actualResponse = userService.create(userRequest);

        assertNotNull(actualResponse);
        assertEquals(expectedResponse, actualResponse);

        verify(keycloakUserService, times(1)).criarUsuarioNoKeycloak(userRequest);
        verify(repository, times(1)).save(userEntity);
        verify(userRequestToUserEntityMapper, times(1)).map(userRequest);
        verify(userEntityToUserResponseCreateMapper, times(1)).map(userEntity);
        verify(bCryptPasswordEncoder, times(1)).encode(userRequest.getPassword());
    }

    @Test
    void testFindByIdUser_Success() {

        Long idUser = 10L;

        UserEntity userEntity = new UserEntity();
        userEntity.setId(idUser);
        userEntity.setFirstName("João");
        userEntity.setLastName("Pedro");
        userEntity.setEmail("joao@hotmail.com");
        userEntity.setBirthday(new Date());
        userEntity.setLogin("joao.pedro");
        userEntity.setPhone("123456789");

        when(repository.findById(idUser)).thenReturn(Optional.of(userEntity));

        UserResponse userResponse = new UserResponse();
        userResponse.setId(userEntity.getId());
        userResponse.setFirstName(userEntity.getFirstName());
        userResponse.setLastName(userEntity.getLastName());
        userResponse.setEmail(userEntity.getEmail());
        userResponse.setBirthday(String.valueOf(userEntity.getBirthday()));
        userResponse.setPhone(userEntity.getPhone());

        when(userEntityToUserResponseMapper.map(userEntity)).thenReturn(userResponse);

        UserResponse actualResponse = userService.findById(idUser);

        assertNotNull(actualResponse);
        assertEquals(actualResponse, userResponse);

        verify(repository, times(1)).findById(idUser);
        verify(userEntityToUserResponseMapper, times(1)).map(userEntity);
    }
}
