package com.fcamara.minhaVaga.model;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class CarPark {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String cnpj;

	@Column(nullable = false)
	private String email;

	@JsonManagedReference
	@OneToMany(mappedBy = "carPark", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<Adress> adress;

	@Column(nullable = false)
	private String phone;

	public CarPark(String name, String password, String cnpj, String email, String phone) {
		this.name = name;
		this.password = password;
		this.cnpj = cnpj;
		this.email = email;
		this.phone = phone;
	}

}
