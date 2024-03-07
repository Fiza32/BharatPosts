package com.blog.services;

import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
	String extractUserName(String token);
	String generateToken(UserDetails userDetails);
	boolean isTokenValid(String token, UserDetails userDetails);
	String generateFreshToken(Map<String, Object> extractClaims, UserDetails userDetails);
}