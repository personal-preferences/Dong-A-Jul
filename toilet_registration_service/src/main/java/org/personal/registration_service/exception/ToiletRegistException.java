package org.personal.registration_service.exception;

import org.personal.registration_service.common.ToiletRegistErrorResult;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ToiletRegistException extends RuntimeException {

	private final ToiletRegistErrorResult errorResult;
}
