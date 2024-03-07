package com.blog.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.model.User;
import com.blog.payloads.JwtAuthenticationResponse;
import com.blog.payloads.RefreshTokenRequest;
import com.blog.payloads.RefreshTokenResponse;
import com.blog.payloads.SignInRequest;
import com.blog.payloads.SignUpRequest;
import com.blog.services.AuthenticationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {
	
	private final AuthenticationService authenticationService;
	
	@PostMapping("/signup")
	public ResponseEntity<User> signUp(@RequestBody SignUpRequest request){
		return new ResponseEntity<>(authenticationService.signUp(request), HttpStatus.OK);
	}
	
	@PostMapping("/login")
	public ResponseEntity<JwtAuthenticationResponse> signIn(@RequestBody SignInRequest request){
		return new ResponseEntity<>(authenticationService.signIn(request), HttpStatus.OK);
	}
	
	@PostMapping("/token/refresh")
	public ResponseEntity<RefreshTokenResponse> refreshToken(@RequestBody RefreshTokenRequest request){
		return new ResponseEntity<RefreshTokenResponse>(authenticationService.refreshToken(request), HttpStatus.OK);
	}
	
	
}
