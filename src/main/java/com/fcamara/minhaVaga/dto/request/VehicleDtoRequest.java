package com.fcamara.minhaVaga.dto.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fcamara.minhaVaga.model.Color;
import com.fcamara.minhaVaga.model.Model;
import com.fcamara.minhaVaga.model.User;
import com.fcamara.minhaVaga.model.Vehicle;
import com.fcamara.minhaVaga.validator.Plate;

import lombok.Getter;

@Getter
public class VehicleDtoRequest {
	
	@NotNull(message = "É preciso informar o modelo do veiculo.")
	@Min(value = 1)
	private Long modelId;
	
	@NotNull(message = "É preciso informar a cor do veiculo.")
	@Min(value = 1)
	private Long colorId;
	
	@NotBlank(message = "A placa deve ser inserida.")
	@Plate
	private String plate;

	public Vehicle convertToVehicle(User user, Color color, Model model) {
		return new Vehicle(user, model, color, this.plate);
	}
}
