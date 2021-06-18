package com.fcamara.minhaVaga.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.security.core.GrantedAuthority;

import lombok.Getter;

@Getter
@Entity
public class Role implements GrantedAuthority{

	private static final long serialVersionUID = -5848560722079935409L;

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private String name;

	@Override
	public String getAuthority() {		
		return this.name;
	}
}
