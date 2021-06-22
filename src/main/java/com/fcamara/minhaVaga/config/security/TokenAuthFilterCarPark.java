package com.fcamara.minhaVaga.config.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import com.fcamara.minhaVaga.model.CarPark;
import com.fcamara.minhaVaga.repository.CarParksRepository;

public class TokenAuthFilterCarPark extends TokenAuthFilter {

	private CarParksRepository carParkRepository;
	
	public TokenAuthFilterCarPark(TokenService tokenService , CarParksRepository carParkRepository) {
		super(tokenService);
		this.carParkRepository = carParkRepository;
	}

	@Override
	public void setAsAuthenticaded(String token) {
		Long userId = tokenService.getRequesterId(token);
		CarPark user = carParkRepository.findById(userId).get();
		UsernamePasswordAuthenticationToken authInfo = new UsernamePasswordAuthenticationToken(user, null, user.getRoles());
		SecurityContextHolder.getContext().setAuthentication(authInfo);
	}
}
