package com.desafiojavapitang.services;

import com.desafiojavapitang.dto.SigninResponse;
import com.desafiojavapitang.dto.UserRequest;
import com.desafiojavapitang.dto.UserResponse;
import com.desafiojavapitang.entities.UserEntity;
import com.desafiojavapitang.dto.SigninRequest;
import com.desafiojavapitang.repositories.UserRepository;
import com.desafiojavapitang.services.mappers.Mapper;
import com.desafiojavapitang.utils.JwtTokenProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

import java.text.SimpleDateFormat;
import java.util.Date;

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

	@Override
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
	public SigninResponse authenticateUser(SigninRequest signinRequest) {

		UserEntity userEntity = repository.findByLogin(signinRequest.getLogin());

		String userName = userEntity.getUsername();
		String password = userEntity.getPassword();

		if(bCryptPasswordEncoder.matches(signinRequest.getPassword(), password)){

			Authentication authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(userName, signinRequest.getPassword()));

			SecurityContextHolder.getContext().setAuthentication(authentication);

			String accessToken = jwtTokenProvider.generateToken(authentication);

			Date birthday = userEntity.getBirthday();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String birthdayFormat = dateFormat.format(birthday);

			return new SigninResponse(userEntity.getFirstName(), userEntity.getLastName(),
					userEntity.getEmail(), birthdayFormat, userEntity.getPhone(), accessToken);
		}
		return null;
	}

	@Override
	public Page<UserResponse> findAllPaged(Pageable pageable) {

		return repository.findAll(pageable).map(userEntityToUserResponseMapper::map);
	}

	@Override
	public UserResponse create(UserRequest userRequest) {

		UserEntity userEntity = userRequestToUserEntityMapper.map(userRequest);

		return userEntityToUserResponseMapper.map(repository.save(userEntity));
	}
}
