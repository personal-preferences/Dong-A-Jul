package org.personal.registration_service.service.impl;

import org.personal.registration_service.common.ToiletRegistErrorResult;
import org.personal.registration_service.domain.ToiletRegist;
import org.personal.registration_service.exception.ToiletRegistException;
import org.personal.registration_service.repository.ToiletRegistRepository;
import org.personal.registration_service.request.ToiletRegistRequest;
import org.personal.registration_service.response.ToiletRegistResponse;
import org.personal.registration_service.service.ToiletRegistService;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ToiletRegistServiceImpl implements ToiletRegistService {

	private final ToiletRegistRepository toiletRegistRepository;

	@Override
	public ToiletRegistResponse addToiletRegist(ToiletRegistRequest request){

		final ToiletRegist toiletRegist = ToiletRegist.of(request);

		final ToiletRegist savedToiletRegist = toiletRegistRepository.save(toiletRegist);

		return ToiletRegistResponse.of(savedToiletRegist);
	}
}
