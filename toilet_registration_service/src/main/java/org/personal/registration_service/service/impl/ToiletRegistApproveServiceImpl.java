package org.personal.registration_service.service.impl;

import static org.personal.registration_service.common.Constants.*;

import java.time.LocalDateTime;

import org.personal.registration_service.common.ToiletRegistErrorResult;
import org.personal.registration_service.domain.ToiletRegist;
import org.personal.registration_service.exception.ToiletRegistException;
import org.personal.registration_service.message.KafkaService;
import org.personal.registration_service.repository.ToiletRegistRepository;
import org.personal.registration_service.request.ToiletLocationRequest;
import org.personal.registration_service.request.ToiletRegistApproveRequest;
import org.personal.registration_service.response.ToiletRegistApproveResponse;
import org.personal.registration_service.response.ToiletRegistResponse;
import org.personal.registration_service.service.ToiletRegistApproveService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ToiletRegistApproveServiceImpl implements ToiletRegistApproveService {

	final ToiletRegistRepository toiletRegistRepository;
	final KafkaService kafkaService;

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

		if(status.equals(APPROVE)){
			kafkaService.sendToiletLocation(ToiletLocationRequest.of(toiletRegist));
		}

		return new ToiletRegistApproveResponse(status);
	}

	@Override
	public ToiletRegistResponse getToiletRegist(long toiletRegistId) {
		ToiletRegist toiletRegist = toiletRegistRepository.findByToiletRegistId(toiletRegistId)
			.orElseThrow(() -> new ToiletRegistException(ToiletRegistErrorResult.ENTITY_NOT_FOUND));
		return ToiletRegistResponse.of(toiletRegist);
	}

	@Override
	public Page<ToiletRegistResponse> listToiletRegist(int pageNum) {
		Pageable pageable = PageRequest.of(pageNum, PAGE_SIZE);

		Page<ToiletRegist> toiletRegists = toiletRegistRepository.findAllByPageable(pageable);

		// 조회된 결과가 비어 있는지 확인
		if (toiletRegists.isEmpty()) {
			throw new ToiletRegistException(ToiletRegistErrorResult.ENTITY_NOT_FOUND);
		}

		return toiletRegists.map(ToiletRegistResponse::of);
	}
}