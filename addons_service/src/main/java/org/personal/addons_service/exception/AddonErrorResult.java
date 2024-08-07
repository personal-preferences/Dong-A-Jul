package org.personal.addons_service.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AddonErrorResult {

	DUPLICATED_ADDON_CREATE(HttpStatus.BAD_REQUEST, "중복된 애드온 Request 입니다."),
	;

	private final HttpStatus httpStatus;
	private final String message;


}
