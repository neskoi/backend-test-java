package com.fcamara.minhaVaga.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class CarPark implements UserDetails{

	private static final long serialVersionUID = 856542516991400376L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

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
	private List<Adress> adress = new ArrayList<>();

	@Column(nullable = false)
	private String phone;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<Role> roles = new ArrayList<>();

	public CarPark(String name, String password, String cnpj, String email, String phone) {
		this.name = name;
		this.password = password;
		this.cnpj = cnpj;
		this.email = email;
		this.phone = phone;
	}

	public List<Adress> getAdress(){
		return this.adress.stream().filter(adress -> adress.getIsActive()).collect(Collectors.toList());
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.roles;
	}

	@Override
	public String getUsername() {		
		return this.email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
