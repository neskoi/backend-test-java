package com.fcamara.minhaVaga.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = PlateValidator.class)
public @interface Plate {
	
	String message() default "A placa precisa ser valida e estar em um dos seguintes formatos: AAA0000 ou AAA0A00";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
