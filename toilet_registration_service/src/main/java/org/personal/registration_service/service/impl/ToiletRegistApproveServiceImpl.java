package org.personal.registration_service.service.impl;

import static org.personal.registration_service.common.Constants.*;

import java.time.LocalDateTime;

import org.personal.registration_service.common.ToiletRegistErrorResult;
import org.personal.registration_service.domain.ToiletRegist;
import org.personal.registration_service.exception.ToiletRegistException;
import org.personal.registration_service.repository.ToiletRegistRepository;
import org.personal.registration_service.request.ToiletRegistApproveRequest;
import org.personal.registration_service.response.ToiletRegistApproveResponse;
import org.personal.registration_service.service.ToiletRegistApproveService;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ToiletRegistApproveServiceImpl implements ToiletRegistApproveService {

	final ToiletRegistRepository toiletRegistRepository;

	@Override
	public ToiletRegistApproveResponse updateToiletRegistApprove(ToiletRegistApproveRequest request) {
		final ToiletRegist toiletRegist = toiletRegistRepository.findById(request.toiletRegistId())
		.orElseThrow(() -> new ToiletRegistException(ToiletRegistErrorResult.ENTITY_NOT_FOUND));

		// 이미 등록 처리된 화장실인지 확인
		if (toiletRegist.getToiletRegistConfirmedDate() != null) {
			throw new ToiletRegistException(ToiletRegistErrorResult.ALREADY_REGISTERED_TOILET);
		}

		toiletRegist.update(request.isApproved(), LocalDateTime.now());
		toiletRegistRepository.save(toiletRegist);

		String status = request.isApproved() ? APPROVE : REJECT;

		return new ToiletRegistApproveResponse(status);
	}
}