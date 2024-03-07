package com.blog.services;

import java.util.HashMap;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.blog.exceptions.InvalidInputException;
import com.blog.exceptions.UserNotFoundException;
import com.blog.model.Role;
import com.blog.model.User;
import com.blog.payloads.JwtAuthenticationResponse;
import com.blog.payloads.RefreshTokenRequest;
import com.blog.payloads.RefreshTokenResponse;
import com.blog.payloads.SignInRequest;
import com.blog.payloads.SignUpRequest;
import com.blog.repositories.UserRepo;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService{
	private final UserRepo userRepo;
	private final AuthenticationManager authenticaionManager;
	private final JwtService jwtService;
	private final PasswordEncoder passwordEncoder;

	@Override
	public User signUp(SignUpRequest request) {
		User user = new User();
		
		user.setName(request.getName());
		user.setEmail(request.getEmail());
		user.setAbout(request.getAbout());
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		user.setRole(parseRole(request.getRole()));
		return user;
	}
	
	private Role parseRole(String role) {
		// TODO Auto-generated method stub
		if("ADMIN".equalsIgnoreCase(role)) {
			return Role.ADMIN;
		}
		else if("USER".equalsIgnoreCase(role)) {
			return Role.USER;
		}
		else {
			throw new InvalidInputException("Provide either Admin Or User in the role field");
		}
	}

	@Override
	public JwtAuthenticationResponse signIn(SignInRequest request) {
		authenticaionManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
		
		User user = userRepo.findByEmail(request.getEmail()).orElseThrow(() -> new UserNotFoundException("User not found with email: " + request.getEmail()));
		
		String jwTokent = jwtService.generateToken(user);
		
		JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
		jwtAuthenticationResponse.setToken(jwTokent);
		
		return jwtAuthenticationResponse;
	}
	
	@Override
	public RefreshTokenResponse refreshToken(RefreshTokenRequest request) {
		String userEmail = jwtService.extractUserName(request.getToken());
		User user = userRepo.findByEmail(userEmail).orElseThrow(() -> new UserNotFoundException("Invalid user email entered!"));
		
		if(jwtService.isTokenValid(request.getToken(), user)) {
			String jwt = jwtService.generateFreshToken(new HashMap<>(), user);
			
			RefreshTokenResponse refreshTokenResponse = new RefreshTokenResponse();
			refreshTokenResponse.setCurrToken(request.getToken());
			refreshTokenResponse.setNewToken(jwt);
			
			return refreshTokenResponse;
		}
		
		
		return null;
	}
}
