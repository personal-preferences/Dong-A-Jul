package org.personal.registration_service.service.impl;

import org.personal.registration_service.common.ToiletRegistErrorResult;
import org.personal.registration_service.domain.ToiletRegist;
import org.personal.registration_service.exception.ToiletRegistException;
import org.personal.registration_service.repository.ToiletRegistRepository;
import org.personal.registration_service.response.ToiletRegistResponse;
import org.personal.registration_service.service.ToiletRegistService;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ToiletRegistServiceImpl implements ToiletRegistService {

	private final ToiletRegistRepository toiletRegistRepository;

	public ToiletRegistResponse addToiletRegist(final Double latitude, final Double longitude){

		final ToiletRegist result = toiletRegistRepository.findByToiletRegistLatitudeAndToiletRegistLongitude(latitude, longitude);

		if(result != null){
			throw new ToiletRegistException(ToiletRegistErrorResult.DUPLICATED_TOILET_REGIST_REGISTER);
		}

		final ToiletRegist toiletRegist = ToiletRegist.builder()
			.toiletRegistLatitude(latitude)
			.toiletRegistLongitude(longitude)
			.build();

		final ToiletRegist savedToiletRegist = toiletRegistRepository.save(toiletRegist);

		return ToiletRegistResponse.of(savedToiletRegist);
	}
}
