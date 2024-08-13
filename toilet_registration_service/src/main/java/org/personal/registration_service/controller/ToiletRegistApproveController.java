package org.personal.registration_service.controller;

import static org.personal.registration_service.common.Constants.*;

import org.personal.registration_service.common.ToiletRegistErrorResult;
import org.personal.registration_service.exception.ToiletRegistException;
import org.personal.registration_service.request.ToiletRegistApproveRequest;
import org.personal.registration_service.response.ToiletRegistApproveResponse;
import org.personal.registration_service.response.ToiletRegistResponse;
import org.personal.registration_service.service.ToiletRegistApproveService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/approves")
public class ToiletRegistApproveController {

	final private ToiletRegistApproveService toiletRegistApproveService;

	@PatchMapping
	public ResponseEntity<ToiletRegistApproveResponse> toiletRegistApprove(
		@RequestHeader(value = USER_ID_HEADER, required = true) final String userId,
		@RequestBody @Valid ToiletRegistApproveRequest request) {

		if (userId == null || userId.isEmpty()) {
			throw new ToiletRegistException(ToiletRegistErrorResult.MISSING_HEADER);
		}

		// 관리자가 아닌 경우 예외 발생
		else if (!userId.equals(ADMIN)) {
			throw new ToiletRegistException(ToiletRegistErrorResult.NOT_ADMIN);
		}

		ToiletRegistApproveResponse response = toiletRegistApproveService.updateToiletRegistApprove(request);

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@GetMapping("{toiletRegistId}")
	public ResponseEntity<ToiletRegistResponse> getToiletRegist(@RequestParam long toiletRegistId){
		ToiletRegistResponse response = toiletRegistApproveService.getToiletRegist(toiletRegistId);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
}
