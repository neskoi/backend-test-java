package com.fcamara.minhaVaga.dto.request;

import javax.validation.constraints.NotNull;

import com.fcamara.minhaVaga.model.TypeOfPayment;

import lombok.Getter;

@Getter
public class CarParkUsageDtoRequest {
	
	
	@NotNull(message = "O tipo de pagamento deve ser informado.")
	private TypeOfPayment typeOfPayment;
}
