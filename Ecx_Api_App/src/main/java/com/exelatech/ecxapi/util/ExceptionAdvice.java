package com.exelatech.ecxapi.util;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ldap.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.exelatech.ecxapi.model.ErrorResponse;

@RestControllerAdvice
public class ExceptionAdvice {


	
	@ExceptionHandler(value = NotFoundException.class)
	public ResponseEntity<ErrorResponse> handleGenericNotFoundException(NotFoundException e, WebRequest request) {
		ErrorResponse errorRes=new ErrorResponse();
		errorRes.setTimestamp(LocalDateTime.now());
		errorRes.setPath(request.getDescription(false));
		errorRes.setMessage(e.getMessage());
		errorRes.setError(HttpStatus.INTERNAL_SERVER_ERROR.name());
		errorRes.setStatus((HttpStatus.INTERNAL_SERVER_ERROR.value()));
		return new ResponseEntity<>(errorRes, HttpStatus.INTERNAL_SERVER_ERROR);
	}	
	
	@ExceptionHandler(value = UserAuthenticateException.class)
	public ResponseEntity<ErrorResponse> handleAuthenticationException(UserAuthenticateException e, WebRequest request) {
		ErrorResponse errorRes=new ErrorResponse();
		errorRes.setTimestamp(LocalDateTime.now());
		errorRes.setPath(request.getDescription(false));
		errorRes.setError(HttpStatus.UNAUTHORIZED.name());
		errorRes.setMessage(e.getMessage());
		errorRes.setStatus((HttpStatus.UNAUTHORIZED.value()));
		return new ResponseEntity<>(errorRes, HttpStatus.UNAUTHORIZED);
	}	
}
