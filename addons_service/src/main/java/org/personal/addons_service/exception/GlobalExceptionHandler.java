package org.personal.addons_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.validation.ConstraintViolationException;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(AddonException.class)
	public ResponseEntity<AddonErrorResult> handleAddonException(AddonException e) {
		AddonErrorResult errorResult = e.getErrorResult();

		if (errorResult == AddonErrorResult.DUPLICATED_ADDON_CREATE) {
			return ResponseEntity.status(HttpStatus.CONFLICT)
				.body(errorResult);
		}

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException e) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
			.body("DTO 객체 @Valid 유효성 검사 실패: " + e.getMessage());
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException e) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
			.body("JPA엔티티 유효성 검사(@NotNull 등) 또는 메서드 파라미터 유효성 검사 실패: " + e.getMessage());
	}
}
