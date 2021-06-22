package com.fcamara.minhaVaga.config.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

public abstract class TokenAuthFilter extends OncePerRequestFilter {

	protected TokenService tokenService;

	public TokenAuthFilter(TokenService tokenService) {
		this.tokenService = tokenService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String token = tokenService.recoverToken(request);
		boolean isValid = tokenService.isTokenValid(token);
		if (isValid)
			setAsAuthenticaded(token);
		filterChain.doFilter(request, response);
	}

	protected abstract void setAsAuthenticaded(String token);
}
