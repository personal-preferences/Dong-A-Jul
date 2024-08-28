package org.personal.registration_service.common;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ToiletRegistErrorResult {

	DUPLICATED_TOILET_REGIST_REGISTER(HttpStatus.BAD_REQUEST, "이미 신청된 화장실 신청입니다."),
	MISSING_HEADER(HttpStatus.BAD_REQUEST, "사용자 식별값이 헤더에 없습니다."),
	NOT_ADMIN(HttpStatus.FORBIDDEN, "관리자 권한이 없습니다."),
	UNKNOWN_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, "알 수 없는 오류가 발생했습니다."),
	INVALID_ARGUMENT(HttpStatus.BAD_REQUEST, "잘못된 인자가 제공되었습니다."),
	NO_SUCH_ELEMENT(HttpStatus.NOT_FOUND, "해당 요소를 찾을 수 없습니다."),
	ALREADY_REGISTERED_TOILET(HttpStatus.BAD_REQUEST, "이미 등록 신청 처리된 화장실입니다."),
	ENTITY_NOT_FOUND(HttpStatus.NOT_FOUND, "요청된 엔티티를 찾을 수 없습니다."),

	// Kafka 관련 예외 추가
	KAFKA_SEND_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "Kafka 메시지 전송에 실패했습니다."),
	KAFKA_ENTITY_NOT_FOUND(HttpStatus.NOT_FOUND, "Kafka에서 요청된 엔티티를 찾을 수 없습니다.");

	private final HttpStatus httpStatus;
	private final String message;
}
