package com.fcamara.minhaVaga.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Vehicle {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Getter(value=AccessLevel.NONE)
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id", nullable = false)
	@JsonIgnore
	private User user;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "model_id", nullable = false)
	private Model model;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "color_id", nullable = false)
	private Color color;

	@Column(nullable = false, unique = true)
	private String plate;

	public Vehicle(User user, Model model, Color color, String plate) {
		this.user = user;
		this.model = model;
		this.color = color;
		this.plate = plate;
	}
	
//	public User getUser() {
//		return null;
//	}

}
