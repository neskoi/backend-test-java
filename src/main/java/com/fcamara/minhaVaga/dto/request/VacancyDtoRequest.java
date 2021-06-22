package com.fcamara.minhaVaga.dto.request;

import java.math.BigDecimal;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fcamara.minhaVaga.model.Adress;
import com.fcamara.minhaVaga.model.TypeOfVehicle;
import com.fcamara.minhaVaga.model.Vacancy;

import lombok.Getter;

@Getter
public class VacancyDtoRequest {

	private Adress adress;

	@NotNull
	private TypeOfVehicle typeOfVehicle;

	@NotNull(message = "A quantia de vagas deve ser preenchida")
	@Min(value = 1, message = "A quantidade de vagas deve ser igual ou maior a 1")
	private int amount;

	@NotNull(message = "O valor do custo hora deve ser preenchido")
	@DecimalMin(value = "0.0", message = "O valor precisa ser igual ou maior a 0")
	private BigDecimal hourPrice;

	@NotNull(message = "O valor do custo dia deve ser preenchido")
	@DecimalMin(value = "0.0", message = "O valor precisa ser igual ou maior a 0")
	private BigDecimal dayPrice;

	@NotNull(message = "O valor do custo mês deve ser preenchido")
	@DecimalMin(value = "0.0", message = "O valor precisa ser igual ou maior a 0")
	private BigDecimal monthPrice;

	public Vacancy convertToVacancy() {	
		return new Vacancy(this.adress, this.typeOfVehicle, this.amount, this.dayPrice, this.hourPrice, this.monthPrice);
	}
}
