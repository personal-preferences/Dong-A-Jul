package org.personal.addons_service.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AddonErrorResult {

	DUPLICATED_ADDON_CREATE(HttpStatus.CONFLICT, "중복된 애드온 Request 입니다."),
	ADDON_NOT_FOUND(HttpStatus.NOT_FOUND, "애드온을 찾을 수 없습니다."),
	;

	private final HttpStatus httpStatus;
	private final String message;


}
