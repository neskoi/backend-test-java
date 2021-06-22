package com.fcamara.minhaVaga.exception;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.MissingServletRequestParameterException;

@RestControllerAdvice
public class AllExceptionHandler {

	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public List<FieldErrorDto> handleValidationException(MethodArgumentNotValidException exception) {
		List<FieldErrorDto> errors = new ArrayList<>();
		List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
		
		fieldErrors.forEach(e ->{
			FieldErrorDto error = new FieldErrorDto(e.getField(), e.getDefaultMessage());
			errors.add(error);
		});
		
		return errors;
	}
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(UserAlreadyExistsException.class)
	public String handleUserAlreadyExistsException(UserAlreadyExistsException exception) {
		return exception.getMessage();
	}
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException exception) {
		return ResponseEntity.ok("Houve um erro de integridade contate o administrador.");
    }
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleHttpMessageNotReadableException(HttpMessageNotReadableException exception) {
		return ResponseEntity.ok("Solicitação mal formada - Cheque o corpo da requisição.");
    }	
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<?> MissingServletRequestParameterException(MissingServletRequestParameterException exception) {
		return ResponseEntity.ok("Solicitação mal formada - Cheque a requisição.");
    }
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<?> ResponseStatusException(ResponseStatusException exception) {
		return ResponseEntity.ok(exception.getMessage());
    }
}
