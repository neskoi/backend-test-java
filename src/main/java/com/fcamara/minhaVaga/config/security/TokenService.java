package com.fcamara.minhaVaga.config.security;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.fcamara.minhaVaga.model.CarPark;
import com.fcamara.minhaVaga.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Service
public class TokenService {
	
	@Value("${minhaVaga.jwt.secret}")
	private String secret;
	
	@Value("${minhaVaga.jwt.expiration}")
	private String expiration;

	private String generateToken(Long id) {
		Date today = new Date();
		Date tokenExpiration = new Date(today.getTime() + Long.parseLong(expiration));
		return Jwts.builder()
				.setIssuer("API Minha Vaga - Gerenciamento de vagas de estacionamento.")
				.setSubject(String.valueOf(id))
				.setIssuedAt(today)
				.setExpiration(tokenExpiration)
				.signWith(io.jsonwebtoken.SignatureAlgorithm.HS256, secret)
				.compact();
	}
	
	public String generateUserToken(Authentication auth) {
		User loged = (User) auth.getPrincipal();
		return generateToken(loged.getId());
	}
	
	public String generateCarParkToken(Authentication auth) {
		CarPark loged = (CarPark) auth.getPrincipal();
		return generateToken(loged.getId());
	}

	public boolean isTokenValid(String token) {
		if(token.isEmpty() || token == null)
			return false;
		
		try {
			Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
	}

	public Long getRequesterId(String token) {
		Claims body = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
		return Long.parseLong(body.getSubject());
	}

	
	public String recoverToken(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		if (token == null || token.isEmpty() || !token.startsWith("Bearer "))
			return null;
		return token.substring(7, token.length());
	}
	
	public Long returnRequesterId(HttpServletRequest request) {
		String token = this.recoverToken(request);
		if(this.isTokenValid(token))
			return this.getRequesterId(token);
		return null; //TODO expectionInvalidToken;
	}

}
