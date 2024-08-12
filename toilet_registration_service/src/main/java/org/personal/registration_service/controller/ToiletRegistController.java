package org.personal.registration_service.controller;

import static org.personal.registration_service.common.Constants.*;

import org.personal.registration_service.common.ToiletRegistErrorResult;
import org.personal.registration_service.exception.ToiletRegistException;
import org.personal.registration_service.request.ToiletRegistRequest;
import org.personal.registration_service.response.ToiletRegistResponse;
import org.personal.registration_service.service.ToiletRegistService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/toiletregists")
public class ToiletRegistController {

	private final ToiletRegistService toiletregistService;

	@PostMapping
	public ResponseEntity<ToiletRegistResponse> addToiletRegist(
		@RequestHeader(value = USER_ID_HEADER, required = true) final String userId,
		@RequestBody @Valid final ToiletRegistRequest request) {

		if (userId == null || userId.isEmpty()) {
			throw new ToiletRegistException(ToiletRegistErrorResult.MISSING_HEADER);
		}

		ToiletRegistResponse response = toiletregistService
			.addToiletRegist(request.toiletRegistLatitude(), request.toiletRegistLongitude());
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
}
