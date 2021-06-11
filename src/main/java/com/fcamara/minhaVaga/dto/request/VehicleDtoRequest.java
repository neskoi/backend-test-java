package com.fcamara.minhaVaga.dto.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fcamara.minhaVaga.validator.Plate;

import lombok.Getter;

@Getter
public class VehicleDtoRequest {
	
	@NotNull(message = "É preciso informar o modelo do veiculo.")
	@Min(value = 1)
	private Long model;
	
	@NotNull(message = "É preciso informar a cor do veiculo.")
	@Min(value = 1)
	private Long color;
	
	@NotBlank(message = "A placa deve ser inserida.")
	@Plate
	private String plate;
}
