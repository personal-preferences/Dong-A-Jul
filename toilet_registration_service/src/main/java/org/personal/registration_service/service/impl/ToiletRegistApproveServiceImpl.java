package org.personal.registration_service.service.impl;

import org.personal.registration_service.common.ToiletRegistErrorResult;
import org.personal.registration_service.domain.ToiletRegist;
import org.personal.registration_service.exception.ToiletRegistException;
import org.personal.registration_service.repository.ToiletRegistRepository;
import org.personal.registration_service.request.ToiletRegistApproveRequest;
import org.personal.registration_service.response.ToiletRegistApproveResponse;
import org.personal.registration_service.service.ToiletRegistApproveService;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ToiletRegistApproveServiceImpl implements ToiletRegistApproveService {

	final ToiletRegistRepository toiletRegistRepository;

	@Override
	public ToiletRegistApproveResponse updateToiletRegistApprove(ToiletRegistApproveRequest request) {
		final ToiletRegist toiletRegist = toiletRegistRepository.findById(request.toiletRegistId())
		.orElseThrow(() -> new ToiletRegistException(ToiletRegistErrorResult.ENTITY_NOT_FOUND));

		return null;
	}
}
