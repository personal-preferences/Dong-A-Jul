package org.personal.registration_service.service.impl;

import org.personal.registration_service.common.ToiletRegistErrorResult;
import org.personal.registration_service.domain.ToiletRegist;
import org.personal.registration_service.exception.ToiletRegistException;
import org.personal.registration_service.repository.ToiletRegistRepository;
import org.personal.registration_service.service.ToiletRegistService;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ToiletRegistServiceImpl implements ToiletRegistService {

	private final ToiletRegistRepository toiletRegistRepository;

	public ToiletRegist addToiletRegist(final Double lat, final Double lng){
		final ToiletRegist result = toiletRegistRepository.findByToiletRegistLatitudeAndToiletRegistLongitude(lat, lng);
		if(result != null){
			throw new ToiletRegistException(ToiletRegistErrorResult.DUPLICATED_TOILET_REGIST_REGISTER);
		}

		return null;
	}
}
