package org.personal.registration_service.common;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ToiletRegistErrorResult {

	DUPLICATED_TOILET_REGIST_REGISTER(HttpStatus.BAD_REQUEST, "이미 신청된 화장실 신청입니다."),
	;

	private final HttpStatus status;
	private final String message;
}
