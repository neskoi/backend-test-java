package com.fcamara.minhaVaga.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.fcamara.minhaVaga.model.Adress;
import com.fcamara.minhaVaga.model.CarPark;

import lombok.Getter;

@Getter
public class CarParkAdressDtoRequest {

	@NotBlank(message = "É preciso informar o logradouro.")
	private String street;

	@NotBlank(message = "É preciso informar um número.")
	@Pattern(regexp = "[\\d]{1,}", message = "Apenas números são aceitos.")
	private String number;

	@NotBlank(message = "É preciso informar o bairro.")
	private String district;

	@NotBlank(message = "É preciso informar a cidade.")
	private String city;

	@NotBlank(message = "É preciso informar o estado.")
	private String state;

	@NotBlank(message = "É preciso informar o CEP.")
	@Pattern(regexp = "[\\d]{1,}", message = "Apenas números são aceitos.")
	private String postalCode;

	private String additionalInfo;

	public Adress convertToAdress(CarPark carPark) {
		return new Adress(carPark, this.street, this.number, this.district, this.city, this.state, this.postalCode,
				this.additionalInfo);
	}
}
