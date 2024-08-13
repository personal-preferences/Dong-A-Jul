package org.personal.registration_service.service.impl;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.personal.registration_service.common.Constants.*;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.personal.registration_service.common.ToiletRegistErrorResult;
import org.personal.registration_service.domain.ToiletRegist;
import org.personal.registration_service.exception.ToiletRegistException;
import org.personal.registration_service.repository.ToiletRegistRepository;
import org.personal.registration_service.request.ToiletRegistApproveRequest;
import org.personal.registration_service.response.ToiletRegistApproveResponse;

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
		final ToiletRegistException result = assertThrows(ToiletRegistException.class, () -> target.updateToiletRegistApprove(requestReject()));

		// then
		assertThat(result.getErrorResult()).isEqualTo(ToiletRegistErrorResult.ENTITY_NOT_FOUND);
	}

	@Test
	public void 화장실등록실패_이미처리된신청임(){
		// given
		ToiletRegist mockToiletRegist = mock(ToiletRegist.class);
		doReturn(Optional.of(mockToiletRegist)).when(toiletRegistRepository).findById(1L);
		doReturn(LocalDateTime.now().toString()).when(mockToiletRegist).getToiletRegistConfirmedDate();

		// when & then
		assertThatThrownBy(() -> target.updateToiletRegistApprove(requestReject()))
			.isInstanceOf(ToiletRegistException.class)
			.hasMessageContaining(  "이미 등록 신청 처리된 화장실입니다.");

	}

	@Test
	public void 화장실등록성공_반려됨(){
		// given
		ToiletRegist mockToiletRegist = mock(ToiletRegist.class);
		doReturn(Optional.of(mockToiletRegist)).when(toiletRegistRepository).findById(1L);
		doReturn(mockToiletRegist).when(toiletRegistRepository).save(any(ToiletRegist.class));

		// when
		final ToiletRegistApproveResponse result = target.updateToiletRegistApprove(requestReject());

		// then
		assertThat(result).isNotNull();
		assertThat(result.message()).isEqualTo(REJECT);
	}

	@Test
	public void 화장실등록성공_승인됨(){
		// given
		ToiletRegist mockToiletRegist = mock(ToiletRegist.class);
		doReturn(Optional.of(mockToiletRegist)).when(toiletRegistRepository).findById(1L);
		doReturn(mockToiletRegist).when(toiletRegistRepository).save(any(ToiletRegist.class));

		// when
		final ToiletRegistApproveResponse result = target.updateToiletRegistApprove(requestApprove());

		// then
		assertThat(result).isNotNull();
		assertThat(result.message()).isEqualTo(APPROVE);
	}

	private ToiletRegistApproveRequest requestReject(){
		return ToiletRegistApproveRequest.builder()
			.toiletRegistId(1L)
			.toiletRegistIsApproved(false)
			.build();
	}

	private ToiletRegistApproveRequest requestApprove(){
		return ToiletRegistApproveRequest.builder()
			.toiletRegistId(1L)
			.toiletRegistIsApproved(true)
			.build();
	}

}