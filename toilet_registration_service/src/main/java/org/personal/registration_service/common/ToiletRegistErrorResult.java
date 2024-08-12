package org.personal.registration_service.common;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ToiletRegistErrorResult {

	DUPLICATED_TOILET_REGIST_REGISTER(HttpStatus.BAD_REQUEST, "이미 신청된 화장실 신청입니다."),
	MISSING_HEADER(HttpStatus.BAD_REQUEST,"사용자 식별값이 헤더에 없습니다."),
	NOT_ADMIN(HttpStatus.FORBIDDEN, "관리자 권한이 없습니다."),
	UNKNOWN_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, "Unknown Exception")
	;

	private final HttpStatus httpStatus;
	private final String message;
}
