package org.personal.addons_service.exception;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.validation.FieldError;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ConstraintViolation;

@ControllerAdvice
public class GlobalExceptionHandler {

	// ErrorResponse 내부 클래스 정의
	private record ErrorResponse(String errorCode, String message, Map<String, String> fieldErrors) {}

	@ExceptionHandler(AddonException.class)
	public ResponseEntity<ErrorResponse> handleAddonException(AddonException e) {
		AddonErrorResult errorResult = e.getErrorResult();
		return ResponseEntity.status(errorResult.getHttpStatus())
			.body(new ErrorResponse(errorResult.name(), errorResult.getMessage(), null));
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException e) {
		Map<String, String> fieldErrors = e.getBindingResult().getFieldErrors().stream()
			.collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));

		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
			.body(new ErrorResponse("VALIDATION_ERROR", "DTO 객체 @Valid 유효성 검사 실패", fieldErrors));
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException e) {
		Map<String, String> fieldErrors = e.getConstraintViolations().stream()
			.collect(Collectors.toMap(
				violation -> violation.getPropertyPath().toString(),
				ConstraintViolation::getMessage
			));

		return ResponseEntity.badRequest()
			.body(new ErrorResponse("CONSTRAINT_VIOLATION", "JPA 엔티티 유효성 검사 실패", fieldErrors));
	}
}
