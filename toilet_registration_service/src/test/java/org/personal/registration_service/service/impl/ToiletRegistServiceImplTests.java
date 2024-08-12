package org.personal.registration_service.service.impl;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.personal.registration_service.common.ToiletRegistErrorResult;
import org.personal.registration_service.domain.ToiletRegist;
import org.personal.registration_service.exception.ToiletRegistException;
import org.personal.registration_service.repository.ToiletRegistRepository;
import org.personal.registration_service.response.ToiletRegistResponse;

@ExtendWith(MockitoExtension.class)
class ToiletRegistServiceImplTests {

	@InjectMocks
	private ToiletRegistServiceImpl target;
	@Mock
	private ToiletRegistRepository toiletRegistRepository;

	private final Double lat = 37.123456;
	private final Double lng = 127.123456;

	@Test
	public void 화장실등록신청실패_이미존재함() {
		// given
		// 스텁이 올바르게 작동하도록 설정
		doReturn(ToiletRegist.builder().build())
			.when(toiletRegistRepository)
			.findByToiletRegistLatitudeAndToiletRegistLongitude(lat, lng);

		// when
		final ToiletRegistException result = assertThrows(ToiletRegistException.class,
			() -> target.addToiletRegist(lat, lng));

		// then
		assertThat(result.getErrorResult()).isEqualTo(ToiletRegistErrorResult.DUPLICATED_TOILET_REGIST_REGISTER);
	}

	@Test
	public void 화장실등록신청성공() {
		// given
		doReturn(null).when(toiletRegistRepository).findByToiletRegistLatitudeAndToiletRegistLongitude(lat, lng);
		doReturn(toiletRegist()).when(toiletRegistRepository).save(any(ToiletRegist.class));

		// when
		final ToiletRegistResponse result = target.addToiletRegist(lat, lng);

		// then
		assertThat(result).isNotNull();
		assertThat(result.toiletRegistId()).isEqualTo(1L);
		assertThat(result.toiletRegistLatitude()).isEqualTo(lat);
		assertThat(result.toiletRegistLongitude()).isEqualTo(lng);

		// verity
		verify(toiletRegistRepository, times(1)).findByToiletRegistLatitudeAndToiletRegistLongitude(lat, lng);
		verify(toiletRegistRepository, times(1)).save(any(ToiletRegist.class));

	}

	private ToiletRegist toiletRegist() {
		return ToiletRegist.builder()
			.toiletRegistId(1L)        // Mockito를 사용하여 save 메서드를 모킹(mocking)할 때, ID가 자동으로 설정되지 않아 설정해줌.
			.toiletRegistLatitude(37.123456)
			.toiletRegistLongitude(127.123456)
			.build();
	}
}