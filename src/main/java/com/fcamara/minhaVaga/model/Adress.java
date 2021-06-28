package com.fcamara.minhaVaga.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Adress {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "car_park_id", nullable = false)
	@JsonBackReference
	private CarPark carPark;

	@Column(nullable = false)
	private String street;

	@Column(nullable = false)
	private String number;

	@Column(nullable = false)
	private String district;

	@Column(nullable = false)
	private String city;

	@Column(nullable = false)
	private String state;

	@Column(nullable = false)
	private String postalCode;

	private String additionalInfo;
	
	@JsonIgnore
	@Column(columnDefinition = "boolean default true")
	private boolean isActive = true;
	
	@JsonManagedReference
	@OneToMany(mappedBy = "adress", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@Fetch (FetchMode.SELECT) 
	private List<Vacancy> vacancy = new ArrayList<>();

	public Adress(CarPark carPark, String street, String number, String district, String city, String state,
			String postalCode, String additionalInfo) {
		this.carPark = carPark;
		this.street = street;
		this.number = number;
		this.district = district;
		this.city = city;
		this.state = state;
		this.postalCode = postalCode;
		this.additionalInfo = additionalInfo;
	}

	public List<Vacancy> getVacancy(){
		return this.vacancy.stream().filter(vacancy -> vacancy.getIsActive()).collect(Collectors.toList());
	}
	
	public boolean getIsActive() {
		return this.isActive;
	}
	
	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}

}
