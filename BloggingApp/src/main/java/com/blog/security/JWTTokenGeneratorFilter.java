package com.blog.security;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import javax.crypto.SecretKey;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.blog.config.AppConstants;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JWTTokenGeneratorFilter extends OncePerRequestFilter {
	
	private final Logger LOG =
            Logger.getLogger(JWTTokenGeneratorFilter.class.getName());
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		LOG.info("Inside the doFilterInternal method of JWTTokenGeneratorFilter");
		// TODO Auto-generated method stub
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		if(authentication != null) {
			SecretKey key = Keys.hmacShaKeyFor(AppConstants.JWT_KEY.getBytes(StandardCharsets.UTF_8));
			
			String jwtString = Jwts.builder().setIssuer("Faizan").setSubject("JWT Token")
					.claim("username", authentication.getName())
					.claim("authorities", populateAuthorities(authentication.getAuthorities()))
					.setIssuedAt(new Date())
					.setExpiration(new Date((new Date()).getTime() + 30000000))
					.signWith(key).compact();
			
			response.setHeader(AppConstants.JWT_HEADER, jwtString);
			LOG.info(jwtString);
		}
		
		filterChain.doFilter(request, response);
		LOG.info("JWT generated successfully___");
	}
	
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {
		return !request.getServletPath().equals("/user");
	}

	private String populateAuthorities(Collection<? extends GrantedAuthority> authorities) {
		// TODO Auto-generated method stub
		Set<String> authoritieSet = new HashSet<>();
		
		for(GrantedAuthority authority : authorities) {
			authoritieSet.add(authority.getAuthority());
		}
		
		return String.join(",", authoritieSet);
	}

}
