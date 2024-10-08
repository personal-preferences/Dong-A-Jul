package org.personal.registration_service.exception;

import org.personal.registration_service.common.ToiletRegistErrorResult;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class ToiletRegistException extends RuntimeException {

	private final ToiletRegistErrorResult errorResult;

	public ToiletRegistException(ToiletRegistErrorResult errorResult) {
		super(errorResult.getMessage());  // RuntimeException의 메시지 필드를 설정
		this.errorResult = errorResult;
	}

}
