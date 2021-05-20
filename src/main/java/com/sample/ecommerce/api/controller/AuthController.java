package com.sample.ecommerce.api.controller;

import java.time.LocalDateTime;

import javax.validation.Valid;

import com.sample.ecommerce.api.model.ExceptionResponse;
import com.sample.ecommerce.api.model.LoginRequest;
import com.sample.ecommerce.api.model.LoginResponse;
import com.sample.ecommerce.security.JwtTokenProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
public class AuthController {
    private final Logger logger = LoggerFactory.getLogger(AuthController.class);
    
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider tokenProvider;
    
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUserName(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(LoginResponse.builder().token(jwt).build());
    }

	@ExceptionHandler({ LockedException.class })
	public ResponseEntity<ExceptionResponse> customHandleException(LockedException ex) {
		logger.error(ex.getMessage(), ex);
		return new ResponseEntity<>(ExceptionResponse.builder().timestamp(LocalDateTime.now()).error(ex.getMessage())
				.status(HttpStatus.FORBIDDEN.value()).build(), HttpStatus.FORBIDDEN);

	}

	@ExceptionHandler({ BadCredentialsException.class })
	public ResponseEntity<ExceptionResponse> customHandleException(BadCredentialsException ex) {
		logger.error(ex.getMessage(), ex);
		return new ResponseEntity<>(ExceptionResponse.builder().timestamp(LocalDateTime.now()).error(ex.getMessage())
				.status(HttpStatus.UNAUTHORIZED.value()).build(), HttpStatus.UNAUTHORIZED);

	}
    
}
