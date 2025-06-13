package com.matchinvest.rest.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<Map<String,String>> handleNotFound(ResourceNotFoundException ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(Map.of("error", ex.getMessage()));
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String,String>> handleValidation(MethodArgumentNotValidException ex) {
		Map<String,String> errors = new HashMap<>();
		ex.getBindingResult().getFieldErrors().forEach(err ->
		errors.put(err.getField(), err.getDefaultMessage())
				);
		return ResponseEntity.badRequest().body(errors);
	}

	//ResponseStatusException já carrega o status correto
	 @ExceptionHandler(ResponseStatusException.class)
	 public ResponseEntity<Map<String,String>> handleResponseStatus(ResponseStatusException ex) {
	   Map<String,String> body = Map.of("error", ex.getReason());
	   return ResponseEntity
	       .status(ex.getStatusCode())
	       .body(body);
	 }
	
	 // Para exceções de violação de constraint de banco
	 @ExceptionHandler(DataIntegrityViolationException.class)
	 public ResponseEntity<Map<String,String>> handleDataIntegrity(DataIntegrityViolationException ex) {
	   Map<String,String> body = Map.of("error", "Violação de regra de integridade");
	   return ResponseEntity
	       .status(HttpStatus.CONFLICT)
	       .body(body);
	 }
	
	 // Qualquer outra exceção fica como 500 com mensagem genérica
	 @ExceptionHandler(Exception.class)
	 public ResponseEntity<Map<String,String>> handleAll(Exception ex) {
	   Map<String,String> body = Map.of("error", "Erro interno do servidor");
	   return ResponseEntity
	       .status(HttpStatus.INTERNAL_SERVER_ERROR)
	       .body(body);
	 }
}