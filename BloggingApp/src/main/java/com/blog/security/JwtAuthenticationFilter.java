package com.blog.security;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.blog.config.AppConstants;
import com.blog.services.JwtService;
import com.blog.services.UserService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtService jwtService;
	private final UserService userService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		final String authHeader = request.getHeader(AppConstants.JWT_HEADER);
		final String jwt;
		final String userEmail;
		
		if(StringUtils.isEmpty(authHeader) || !StringUtils.startsWith(authHeader, AppConstants.AUTHENTICATION_SCHEME)) {
			filterChain.doFilter(request, response);
			return;
		}
		
		jwt = authHeader.substring(7);
		userEmail = jwtService.extractUserName(jwt);
		
		if(StringUtils.isNotEmpty(userEmail) && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = userService.userDetailsService().loadUserByUsername(userEmail);
			
			if(jwtService.isTokenValid(jwt, userDetails)) {
				SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
				
				UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				
				token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				securityContext.setAuthentication(token);
				SecurityContextHolder.setContext(securityContext);
			}
		}
		
		filterChain.doFilter(request, response);
	}

}
