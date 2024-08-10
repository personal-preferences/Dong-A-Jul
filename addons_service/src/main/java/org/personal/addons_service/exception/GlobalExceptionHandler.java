package org.personal.addons_service.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.validation.ConstraintViolationException;

@ControllerAdvice
public class GlobalExceptionHandler {

	// ErrorResponse 내부 클래스 정의
	private record ErrorResponse(String errorCode, String message) {}

	@ExceptionHandler(AddonException.class)
	public ResponseEntity<ErrorResponse> handleAddonException(AddonException e) {
		AddonErrorResult errorResult = e.getErrorResult();

		return ResponseEntity.status(errorResult.getHttpStatus())
			.body(new ErrorResponse(errorResult.name(), errorResult.getMessage()));
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException e) {
		return ResponseEntity.status(e.getStatusCode())
			.body(new ErrorResponse("VALIDATION_ERROR", "DTO 객체 @Valid 유효성 검사 실패: "
				+ e.getBindingResult().getAllErrors().get(0).getDefaultMessage()));
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException e) {
		return ResponseEntity.badRequest()
			.body(new ErrorResponse("CONSTRAINT_VIOLATION", "JPA엔티티 유효성 검사(@NotNull 등) 또는 메서드 파라미터 유효성 검사 실패: "
				+ e.getMessage()));
	}
}
