package com.fcamara.minhaVaga.config.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import com.fcamara.minhaVaga.model.User;
import com.fcamara.minhaVaga.repository.UserRepository;

public class TokenAuthFilterUser extends TokenAuthFilter {

	private UserRepository userRepository;
	
	public TokenAuthFilterUser(TokenService tokenService , UserRepository userRepository) {
		super(tokenService);
		this.userRepository = userRepository;
	}

	@Override
	public void setAsAuthenticaded(String token) {
		Long userId = tokenService.getRequesterId(token);
		User user = userRepository.findById(userId).get();
		UsernamePasswordAuthenticationToken authInfo = new UsernamePasswordAuthenticationToken(user, null, user.getRoles());
		SecurityContextHolder.getContext().setAuthentication(authInfo);
	}
}
