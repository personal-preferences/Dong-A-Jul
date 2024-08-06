package org.personal.registration_service.service;

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
import org.personal.registration_service.service.impl.ToiletRegistServiceImpl;

@ExtendWith(MockitoExtension.class)
class ToiletRegistServiceTests {

	@InjectMocks
	private ToiletRegistServiceImpl target;
	@Mock
	private ToiletRegistRepository toileRegistRepository;

	private final Double lat = 37.123456;
	private final Double lng = 127.123456;

	@Test
	public void 화장실등록실패_이미존재함(){
		// given
		doReturn(ToiletRegist.builder().build()).when(toileRegistRepository).findByToiletRegistLatitudeAndToiletRegistLongitude(lat,lng);

		// when
		final ToiletRegistException result = assertThrows(ToiletRegistException.class, () -> target.addToiletRegist(lat, lng));

		// then
		assertThat(result.getErrorResult()).isEqualTo(ToiletRegistErrorResult.DUPLICATED_TOILET_REGIST_REGISTER);
	}

}