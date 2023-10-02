package com.desafiojavapitang.services;

import com.desafiojavapitang.dto.SigninRequest;
import com.desafiojavapitang.dto.SigninResponse;
import com.desafiojavapitang.dto.UserRequest;
import com.desafiojavapitang.dto.UserResponse;
import com.desafiojavapitang.entities.UserEntity;
import com.desafiojavapitang.repositories.UserRepository;
import com.desafiojavapitang.services.mappers.Mapper;
import com.desafiojavapitang.utils.JwtTokenProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class UserServiceImpl implements UserDetailsService, UserService {
	
	private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	private UserRepository repository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	@Lazy
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Autowired
	private Mapper<UserEntity, UserResponse> userEntityToUserResponseMapper;

	@Autowired
	private Mapper<UserRequest, UserEntity> userRequestToUserEntityMapper;

	@Autowired
	private Mapper<UserEntity, SigninResponse> userEntityToSigninResponseMapper;

	@Value("${jwt.secret}")
	private String jwtSecret;

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		UserEntity user = repository.findByEmail(username);
		
		if(user == null) {
			logger.error("User not found: " + username);
			throw new UsernameNotFoundException("Email not found");
		}
		
		logger.info("User found: " + username);
		return user; 
	}

	@Override
	@Transactional
	public SigninResponse authenticateUser(SigninRequest signinRequest) {

		UserEntity userEntity = repository.findByLogin(signinRequest.getLogin());

		String userName = userEntity.getUsername();
		String password = userEntity.getPassword();

		if(bCryptPasswordEncoder.matches(signinRequest.getPassword(), password)){

			Authentication authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(userName, signinRequest.getPassword()));

			SecurityContextHolder.getContext().setAuthentication(authentication);

			String accessToken = jwtTokenProvider.generateToken(authentication);

			userEntity.setLastLogin(Instant.now());
			userEntity = repository.save(userEntity);

			SigninResponse signinResponse = userEntityToSigninResponseMapper.map(userEntity);

			signinResponse.setAccessToken(accessToken);

			return signinResponse;
		}
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public Page<UserResponse> findAllPaged(Pageable pageable) {

		return repository.findAll(pageable).map(userEntityToUserResponseMapper::map);
	}

	@Override
	@Transactional
	public UserResponse create(UserRequest userRequest) {

		UserEntity userEntity = userRequestToUserEntityMapper.map(userRequest);
		userEntity.setPassword(bCryptPasswordEncoder.encode(userRequest.getPassword()));

		return userEntityToUserResponseMapper.map(repository.save(userEntity));
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

	@Override
	@Transactional(readOnly = true)
	public UserResponse findAuthenticateUser(String token) {

		Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
		String username = claims.getSubject();

		UserEntity userEntity = repository.findByEmail(username);

		return userEntityToUserResponseMapper.map(userEntity);
	}
}
