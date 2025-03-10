package com.desafiojavapitang.services;

import com.desafiojavapitang.dto.*;
import com.desafiojavapitang.entities.UserEntity;
import com.desafiojavapitang.repositories.UserRepository;
import com.desafiojavapitang.services.exceptions.EmailAlreadyException;
import com.desafiojavapitang.services.exceptions.InvalidLoginOrPasswordException;
import com.desafiojavapitang.services.exceptions.LoginAlreadyException;
import com.desafiojavapitang.services.exceptions.MissingFieldsException;
import com.desafiojavapitang.services.mappers.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.Instant;
import java.util.Date;

@Service
public class UserServiceImpl implements UserService {
	
	private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	private UserRepository repository;

	@Autowired
	private CarService carService;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private Mapper<UserEntity, UserResponse> userEntityToUserResponseMapper;

	@Autowired
	private Mapper<UserEntity, UserResponseCreate> userEntityToUserResponseCreateMapper;

	@Autowired
	private Mapper<UserRequest, UserEntity> userRequestToUserEntityMapper;

	@Autowired
	private Mapper<UserEntity, SigninResponse> userEntityToSigninResponseMapper;

	@Autowired
	private KeycloakUserServiceImpl keycloakUserService;

	@Override
	@Transactional
	public SigninResponse authenticateUser(SigninRequest signinRequest) {

		UserEntity userEntity = repository.findByLogin(signinRequest.getLogin());
		if(userEntity == null){
			throw new InvalidLoginOrPasswordException("Invalid login or password");
		}

		String userName = userEntity.getLogin();
		String password = userEntity.getPassword();

		boolean isMatcherPassword = bCryptPasswordEncoder.matches(signinRequest.getPassword(), password);
		if(!isMatcherPassword){
			throw new InvalidLoginOrPasswordException("Invalid login or password");
		}

        String accessToken = null;
        try {
            accessToken = keycloakUserService.obterTokenUsuario(userName, signinRequest.getPassword());
        } catch (IOException e) {
            throw new RuntimeException("Erro ao recuperar token do usuário no Keycloak");
        }

        userEntity.setLastLogin(Instant.now());
		userEntity = repository.save(userEntity);

		SigninResponse signinResponse = userEntityToSigninResponseMapper.map(userEntity);

		signinResponse.setAccessToken(accessToken);

		return signinResponse;
	}

	@Override
	@Transactional(readOnly = true)
	public Page<UserResponse> findAllPaged(Pageable pageable) {

		return repository.findAll(pageable).map(userEntityToUserResponseMapper::map);
	}

	@Override
	@Transactional
	public UserResponseCreate create(UserRequest userRequest) {

		validateAtributtes(userRequest);

		try {
			keycloakUserService.criarUsuarioNoKeycloak(userRequest);
		} catch (IOException e) {
			throw new RuntimeException("Erro ao criar usuário no Keycloak");
		}

		UserEntity userEntity = userRequestToUserEntityMapper.map(userRequest);
		userEntity.setPassword(bCryptPasswordEncoder.encode(userRequest.getPassword()));

		return userEntityToUserResponseCreateMapper.map(repository.save(userEntity));
	}

	@Override
	@Transactional(readOnly = true)
	public UserResponse findById(Long id) {

		UserEntity userEntity = repository.findById(id).orElseThrow(() -> new RuntimeException("Entity not found: " + id));
		return userEntityToUserResponseMapper.map(userEntity);
	}

	@Override
	@Transactional
	public void delete(Long id) {

		repository.deleteById(id);
	}

	@Override
	@Transactional
	public UserResponse update(Long id, UserRequest userRequest) {

		validateAtributtes(userRequest);

		UserEntity userEntity = repository.getOne(id);

		userEntity.setFirstName(userRequest.getFirstName());
		userEntity.setLastName(userRequest.getLastName());
		userEntity.setEmail(userRequest.getEmail());
		userEntity.setBirthday(userRequest.getBirthday());
		userEntity.setLogin(userRequest.getLogin());
		userEntity.setPassword(bCryptPasswordEncoder.encode(userRequest.getPassword()));
		userEntity.setPhone(userRequest.getPhone());

		return userEntityToUserResponseMapper.map(repository.save(userEntity));
	}

	public void validateAtributtes(UserRequest userRequest) {

		boolean existsEmail = repository.existsByEmail(userRequest.getEmail());
		if(existsEmail){
			throw new EmailAlreadyException("Email already exists");
		}

		boolean existsLogin = repository.existsByLogin(userRequest.getLogin());
		if(existsLogin){
			throw new LoginAlreadyException("Login already exists");
		}

		String firstName = userRequest.getFirstName();
		String lastName = userRequest.getLastName();
		String email = userRequest.getEmail();
		Date birthday = userRequest.getBirthday();
		String login = userRequest.getLogin();
		String password = userRequest.getPassword();
		String phone = userRequest.getPhone();

		if( (firstName == null || firstName.isEmpty() || firstName.isBlank())
				|| (lastName == null || lastName.isEmpty() || lastName.isBlank())
				|| (email == null || email.isEmpty() || email.isBlank())
				|| birthday == null
				|| (login == null || login.isEmpty() || login.isBlank())
				|| (password == null || password.isEmpty() || password.isBlank())
				|| (phone == null || phone.isEmpty() || phone.isBlank())){

			throw new MissingFieldsException("Missing fields");
		}

		userRequest.getCars().forEach(car -> {
			carService.validateAtributtes(car);
		});
	}

	@Override
	@Transactional
	public void deleteAll() {
		repository.deleteAll();
	}

	@Override
	@Transactional(readOnly = true)
	public UserResponse findAuthenticateUser(String token) {

//		if(token == null || token.isEmpty() || token.isBlank()){
//			throw new InvalidTokenJWTExeption("Unauthorized");
//		}
//
//		try{
//			Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
//
//			String username = claims.getSubject();
//
//			UserEntity userEntity = repository.findByEmail(username);
//
//			return userEntityToUserResponseMapper.map(userEntity);
//
//		} catch (Exception e){
//			if(e instanceof ExpiredJwtException){
//				throw new InvalidTokenJWTExeption("Unauthorized - invalid session");
//			}
//		}
		return null;
	}

}
