package org.personal.registration_service.controller;

import org.personal.registration_service.request.ToiletRegistRequest;
import org.personal.registration_service.response.ToiletRegistResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/toiletregists")
public class ToiletRegistController {

	@PostMapping
	public ResponseEntity<ToiletRegistResponse> addToiletRegist(
		@RequestHeader() final String userId,
		@RequestBody final ToiletRegistRequest toiletRegistRequest){

		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

}
