package org.personal.registration_service.service.impl;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.personal.registration_service.common.ToiletRegistErrorResult;
import org.personal.registration_service.exception.ToiletRegistException;
import org.personal.registration_service.repository.ToiletRegistRepository;
import org.personal.registration_service.request.ToiletRegistApproveRequest;

@ExtendWith(MockitoExtension.class)
class ToiletRegistApproveServiceImplTests {

	@InjectMocks
	private ToiletRegistApproveServiceImpl target;

	@Mock
	private ToiletRegistRepository toiletRegistRepository;

	@Test
	public void 화장실등록실패_등록된화장실없음() {
		// given
		when(toiletRegistRepository.findById(1L)).thenReturn(Optional.empty());

		// when
		final ToiletRegistException result = assertThrows(ToiletRegistException.class, () -> target.updateToiletRegistApprove(request()));

		// then
		assertThat(result.getErrorResult()).isEqualTo(ToiletRegistErrorResult.ENTITY_NOT_FOUND);

	}

	private ToiletRegistApproveRequest request(){
		return ToiletRegistApproveRequest.builder()
			.toiletRegistId(1L)
			.toiletRegistIsApproved(false)
			.build();
	}

}