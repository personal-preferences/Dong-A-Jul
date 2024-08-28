package org.personal.registration_service.exception;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.personal.registration_service.common.ToiletRegistErrorResult;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
		final MethodArgumentNotValidException ex,
		final HttpHeaders headers,
		final HttpStatusCode status,
		final WebRequest request) {

		final List<String> errorList = ex.getBindingResult()
			.getAllErrors()
			.stream()
			.map(DefaultMessageSourceResolvable::getDefaultMessage)
			.collect(Collectors.toList());

		log.warn("Invalid DTO Parameter errors : {}", errorList);
		return this.makeErrorResponseEntity(errorList.toString());
	}

	private ResponseEntity<Object> makeErrorResponseEntity(final String errorDescription) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
			.body(new ErrorResponse(HttpStatus.BAD_REQUEST.toString(), errorDescription));
	}

	@ExceptionHandler({ToiletRegistException.class})
	public ResponseEntity<ErrorResponse> handleToiletRegistException(final ToiletRegistException exception) {
		log.warn("ToiletRegistException occur: ", exception);
		return this.makeErrorResponseEntity(exception.getErrorResult(), exception.getMessage());
	}

	@ExceptionHandler({IllegalArgumentException.class})
	public ResponseEntity<ErrorResponse> handleIllegalArgumentException(final IllegalArgumentException exception) {
		log.warn("IllegalArgumentException occur: ", exception);
		return this.makeErrorResponseEntity(ToiletRegistErrorResult.INVALID_ARGUMENT, exception.getMessage());
	}

	@ExceptionHandler({NoSuchElementException.class})
	public ResponseEntity<ErrorResponse> handleNoSuchElementException(final NoSuchElementException exception) {
		log.warn("NoSuchElementException occur: ", exception);
		return this.makeErrorResponseEntity(ToiletRegistErrorResult.NO_SUCH_ELEMENT, exception.getMessage());
	}

	@ExceptionHandler({jakarta.persistence.EntityNotFoundException.class})
	public ResponseEntity<ErrorResponse> handleEntityNotFoundException(final jakarta.persistence.EntityNotFoundException exception) {
		log.warn("EntityNotFoundException occur: ", exception);
		return this.makeErrorResponseEntity(ToiletRegistErrorResult.ENTITY_NOT_FOUND, exception.getMessage());
	}

	@ExceptionHandler(KafkaRegistException.class)
	public ResponseEntity<ErrorResponse> handleKafkaRegistException(final KafkaRegistException ex) {
		log.warn("KafkaRegistException occur: ", ex);
		return this.makeErrorResponseEntity(ex.getErrorResult(), ex.getMessage());
	}

	@ExceptionHandler({Exception.class})
	public ResponseEntity<ErrorResponse> handleException(final Exception exception) {
		log.warn("Exception occur: ", exception);
		return this.makeErrorResponseEntity(ToiletRegistErrorResult.UNKNOWN_EXCEPTION, exception.getMessage());
	}

	private ResponseEntity<ErrorResponse> makeErrorResponseEntity(final ToiletRegistErrorResult errorResult) {
		return ResponseEntity.status(errorResult.getHttpStatus())
			.body(new ErrorResponse(errorResult.name(), errorResult.getMessage()));
	}

	private ResponseEntity<ErrorResponse> makeErrorResponseEntity(final ToiletRegistErrorResult errorResult, final String message) {
		return ResponseEntity.status(errorResult.getHttpStatus())
			.body(new ErrorResponse(errorResult.name(), message));
	}

	record ErrorResponse(String code, String message) {
	}
}
