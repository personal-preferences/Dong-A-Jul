package org.personal.registration_service.exception;

import org.personal.registration_service.common.ToiletRegistErrorResult;

import lombok.Getter;

@Getter
public class KafkaRegistException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private final ToiletRegistErrorResult errorResult;

	public KafkaRegistException(ToiletRegistErrorResult errorResult) {
		super(errorResult.getMessage());
		this.errorResult = errorResult;
	}
}
