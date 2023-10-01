package com.desafiojavapitang.services;

import com.desafiojavapitang.dto.SigninResponse;
import com.desafiojavapitang.entities.UserEntity;
import com.desafiojavapitang.dto.SigninRequest;
import com.desafiojavapitang.repositories.UserRepository;
import com.desafiojavapitang.utils.JwtTokenProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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

			return new SigninResponse(userEntity.getFirstName(), userEntity.getLastName(),
					userEntity.getEmail(), userEntity.getBirthday(), userEntity.getPhone(), accessToken);
		}
		return null;
	}

}
