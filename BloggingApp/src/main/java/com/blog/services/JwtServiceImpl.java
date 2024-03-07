package com.blog.services;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;

import com.blog.config.AppConstants;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

public class JwtServiceImpl implements JwtService{


	@Override
	public String generateToken(UserDetails userDetails) {
		// TODO Auto-generated method stub
		return Jwts.builder()
			.setSubject(userDetails.getUsername())
			.setIssuedAt(new Date(System.currentTimeMillis()))
			.setExpiration(new Date(System.currentTimeMillis() + AppConstants.TOKEN_VALIDITY))
			.signWith(getSigninKey(), SignatureAlgorithm.HS256)
			.compact();
	}
	
	@Override
	public String extractUserName(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	private Key getSigninKey() {
		// TODO Auto-generated method stub
		byte[] key = Decoders.BASE64.decode(AppConstants.JWT_KEY);
		return Keys.hmacShaKeyFor(key);
	}
	
	private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
		final Claims claims = getAllClaims(token);
		return claimsResolvers.apply(claims);
	}
	
	private Claims getAllClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(getSigninKey()).build().parseClaimsJws(token).getBody();
	}

	@Override
	public boolean isTokenValid(String token, UserDetails userDetails) {
		// TODO Auto-generated method stub
		final String username = extractUserName(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
	
	private boolean isTokenExpired(String token) {
		return extractClaim(token, Claims::getExpiration).before(new Date());
	}

	@Override
	public String generateFreshToken(Map<String, Object> allClaims, UserDetails userDetails) {
		return Jwts.builder().setClaims(allClaims)
				.setSubject(userDetails.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + AppConstants.TOKEN_VALIDITY))
				.signWith(getSigninKey(), SignatureAlgorithm.HS256)
				.compact();
	}

}
