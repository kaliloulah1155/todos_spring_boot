package com.ibson.todos.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ibson.todos.request.AuthenticationRequest;
import com.ibson.todos.request.RegisterRequest;
import com.ibson.todos.response.AuthenticationResponse;
import com.ibson.todos.service.AuthenticationService;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication REST API Endpoints",description = "Operations related to register & login")
public class AuthenticationController {
	
	private final AuthenticationService authenticationService;
	
	public AuthenticationController(AuthenticationService authenticationService) {
		this.authenticationService=authenticationService;
	}

	//pwd P@ssword!123
	@Operation(summary="Register a user",description = "Create a new user in database")
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/register")
	public void register(@Valid @RequestBody RegisterRequest registerRequest) throws Exception{
		authenticationService.register(registerRequest);
	}
	
	@Operation(summary = "Login a User",description = "Submit email & password to authenticate user")
	@ResponseStatus(HttpStatus.OK)
	@PostMapping("/login")
	public AuthenticationResponse login(@Valid @RequestBody AuthenticationRequest authRequest) {
		return authenticationService.login(authRequest);
	}
}




















