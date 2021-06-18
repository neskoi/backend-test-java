package com.fcamara.minhaVaga.config.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.fcamara.minhaVaga.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Service
public class TokenService {
	
	@Value("${minhaVaga.jwt.secret}")
	private String secret;
	
	@Value("${minhaVaga.jwt.expiration}")
	private String expiration;

	public String generateToken(Authentication auth) {
		User loged = (User) auth.getPrincipal();
		Date today = new Date();
		Date tokenExpiration = new Date(today.getTime() + Long.parseLong(expiration));
		return Jwts.builder()
				.setIssuer("API Minha Vaga - Gerenciamento de vagas de estacionamento.")
				.setSubject(String.valueOf(loged.getId()))
				.setIssuedAt(today)
				.setExpiration(tokenExpiration)
				.signWith(io.jsonwebtoken.SignatureAlgorithm.HS256, secret)
				.compact();
	}

	public boolean isTokenValido(String token) {
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

}
