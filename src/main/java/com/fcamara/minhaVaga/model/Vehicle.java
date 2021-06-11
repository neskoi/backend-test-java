package com.fcamara.minhaVaga.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

@Data
@Entity
public class Vehicle {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id", nullable = false, insertable = false, updatable = false)
	private User user;
	
	@Column(name = "user_id")
	private Long UserId;
	
	@ManyToOne(targetEntity = Model.class, fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "model_id", nullable = false, insertable = false, updatable = false)
	private Model model;
	
	@Column(name = "model_id")
	private Long modelId;

	@ManyToOne(targetEntity = Color.class, fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "color_id", nullable = false, insertable = false, updatable = false)
	private Color color;
	
	@Column(name = "color_id")
	private Long colorId;
	
	@Column(nullable = false)
	private String plate;

}
