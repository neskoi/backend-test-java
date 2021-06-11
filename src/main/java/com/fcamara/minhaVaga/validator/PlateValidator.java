package com.fcamara.minhaVaga.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PlateValidator implements ConstraintValidator<Plate, String> {

	@Override
	public boolean isValid(String plate, ConstraintValidatorContext arg1) {
		if (plate != null)
			if (plate.matches("[A-Z]{3}\\d{4}") && plate.length() == 7)
				return true;
		return false;
	}

}
