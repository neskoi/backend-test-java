package com.fcamara.minhaVaga.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private String password;

	@Column(unique = true, nullable = false)
	private String cpf;

	@Column(unique = true, nullable = false)
	private String email;

	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<Vehicle> vehicle;

	public User(String name, String password, String cpf, String email) {
		this.name = name;
		this.password = password;
		this.cpf = cpf;
		this.email = email;
	}
}
