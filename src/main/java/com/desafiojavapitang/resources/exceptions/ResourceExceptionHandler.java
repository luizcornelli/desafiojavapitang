package com.desafiojavapitang.resources.exceptions;

import com.desafiojavapitang.services.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler(InvalidLoginOrPasswordException.class)
	public ResponseEntity<StandardError> invalidLoginOrPasswordException(InvalidLoginOrPasswordException e, HttpServletRequest request) {

		HttpStatus status = HttpStatus.NOT_FOUND;

		StandardError err = new StandardError();

		err.setErrorCode(status.value());
		err.setMessage(e.getMessage());

		return ResponseEntity.status(status).body(err);
	}

	@ExceptionHandler(EmailAlreadyException.class)
	public ResponseEntity<StandardError> emailAlreadyException(EmailAlreadyException e, HttpServletRequest request) {

		HttpStatus status = HttpStatus.CONFLICT;

		StandardError err = new StandardError();

		err.setErrorCode(status.value());
		err.setMessage(e.getMessage());

		return ResponseEntity.status(status).body(err);
	}

	@ExceptionHandler(LoginAlreadyException.class)
	public ResponseEntity<StandardError> loginAlreadyException(LoginAlreadyException e, HttpServletRequest request) {

		HttpStatus status = HttpStatus.CONFLICT;

		StandardError err = new StandardError();

		err.setErrorCode(status.value());
		err.setMessage(e.getMessage());

		return ResponseEntity.status(status).body(err);
	}

	@ExceptionHandler(MissingFieldsException.class)
	public ResponseEntity<StandardError> missingFieldsException(MissingFieldsException e, HttpServletRequest request) {

		HttpStatus status = HttpStatus.BAD_REQUEST;

		StandardError err = new StandardError();

		err.setErrorCode(status.value());
		err.setMessage(e.getMessage());

		return ResponseEntity.status(status).body(err);
	}

	@ExceptionHandler(InvalidTokenJWTExeption.class)
	public ResponseEntity<StandardError> invalidTokenJWTExeption(InvalidTokenJWTExeption e, HttpServletRequest request) {

		HttpStatus status = HttpStatus.UNAUTHORIZED;

		StandardError err = new StandardError();

		err.setErrorCode(status.value());
		err.setMessage(e.getMessage());

		return ResponseEntity.status(status).body(err);
	}

	@ExceptionHandler(LicensePlateAlreadyExistsException.class)
	public ResponseEntity<StandardError> licensePlateAlreadyExistsException(LicensePlateAlreadyExistsException e, HttpServletRequest request) {

		HttpStatus status = HttpStatus.CONFLICT;

		StandardError err = new StandardError();

		err.setErrorCode(status.value());
		err.setMessage(e.getMessage());

		return ResponseEntity.status(status).body(err);
	}
}
