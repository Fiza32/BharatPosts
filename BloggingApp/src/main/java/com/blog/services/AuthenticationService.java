package com.blog.services;


import com.blog.model.User;
import com.blog.payloads.JwtAuthenticationResponse;
import com.blog.payloads.RefreshTokenRequest;
import com.blog.payloads.RefreshTokenResponse;
import com.blog.payloads.SignInRequest;
import com.blog.payloads.SignUpRequest;


public interface AuthenticationService {
	User signUp(SignUpRequest request);
	JwtAuthenticationResponse signIn(SignInRequest request);
	RefreshTokenResponse refreshToken(RefreshTokenRequest request);
} 
