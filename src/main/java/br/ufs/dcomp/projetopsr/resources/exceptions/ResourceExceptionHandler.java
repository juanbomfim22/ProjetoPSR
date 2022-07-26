package br.ufs.dcomp.projetopsr.resources.exceptions;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.ufs.dcomp.projetopsr.services.exceptions.AuthorizationException;
import br.ufs.dcomp.projetopsr.services.exceptions.DataIntegrityException;
import br.ufs.dcomp.projetopsr.services.exceptions.ObjectNotFoundException;

@ControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e, HttpServletRequest request) {
		HttpStatus status = HttpStatus.NOT_FOUND;

		StandardError err = new StandardError(status.value(), e.getMessage(), System.currentTimeMillis());

		return ResponseEntity.status(status).body(err);
	}

	// Pense assim:
	// Esse método roda a exception quando a classe DataIntegrityException é
	// invocada
	@ExceptionHandler(DataIntegrityException.class)
	public ResponseEntity<StandardError> dataIntegrity(DataIntegrityException e, HttpServletRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST;

		StandardError err = new StandardError(status.value(), e.getMessage(), System.currentTimeMillis());

		return ResponseEntity.status(status).body(err);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandardError> validation(MethodArgumentNotValidException e, HttpServletRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		ValidationError err = new ValidationError(status.value(), "Erro de validação", System.currentTimeMillis());

		for(FieldError x : e.getBindingResult().getFieldErrors()) {
			err.addError(x.getField(), x.getDefaultMessage());
		}
		
		return ResponseEntity.status(status).body(err);

	}
	
	@ExceptionHandler(AuthorizationException.class)
	public ResponseEntity<StandardError> authorization(AuthorizationException e, HttpServletRequest request) {
		HttpStatus status = HttpStatus.FORBIDDEN;
	
		StandardError err = new StandardError(status.value(), e.getMessage(), System.currentTimeMillis());
	
		return ResponseEntity.status(status).body(err);
	}
	
	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ResponseEntity<StandardError> validation(MissingServletRequestParameterException e, HttpServletRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		ValidationError err = new ValidationError(status.value(), "Faltam parametros: "+e.getLocalizedMessage(), System.currentTimeMillis());
		
		 
		
		return ResponseEntity.status(status).body(err);

	}
	
}