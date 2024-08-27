package org.personal.registration_service.service.impl;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.personal.registration_service.domain.ToiletRegist;
import org.personal.registration_service.repository.ToiletRegistRepository;
import org.personal.registration_service.request.ToiletRegistRequest;
import org.personal.registration_service.response.ToiletRegistResponse;

import java.time.LocalDateTime;

@ExtendWith(MockitoExtension.class)
class ToiletRegistServiceImplTests {

	@InjectMocks
	private ToiletRegistServiceImpl target;

	@Mock
	private ToiletRegistRepository toiletRegistRepository;

	private final Double lat = 37.123456;
	private final Double lng = 127.123456;

	@Test
	public void 화장실등록신청성공() {
		// given
		ToiletRegist mockToiletRegist = toiletRegist();
		doReturn(mockToiletRegist).when(toiletRegistRepository).save(any(ToiletRegist.class));

		// when
		final ToiletRegistResponse result = target.addToiletRegist(toiletRegistRequest(lat, lng));

		// then
		assertThat(result).isNotNull();
		assertThat(result.toiletRegistId()).isEqualTo(mockToiletRegist.getToiletRegistId());
		assertThat(result.toiletRegistLatitude()).isEqualTo(lat);
		assertThat(result.toiletRegistLongitude()).isEqualTo(lng);

		// 검증
		verify(toiletRegistRepository, times(1)).save(any(ToiletRegist.class));
	}

	private ToiletRegist toiletRegist() {
		return ToiletRegist.builder()
			.toiletRegistId(1L)
			.toiletRegistLatitude(lat)
			.toiletRegistLongitude(lng)
			.toiletRegistDate(LocalDateTime.now().toString())
			.build();
	}

	private ToiletRegistRequest toiletRegistRequest(final Double lat, final Double lng) {
		return ToiletRegistRequest.builder()
			.toiletRegistLatitude(lat)
			.toiletRegistLongitude(lng)
			.toiletRegistToiletName("예시 화장실")
			.toiletRegistRoadNameAddress("예시 도로명 주소")
			.toiletRegistNumberAddress("123-456")
			.toiletRegistManagementAgency("예시 관리기관")
			.toiletRegistPhoneNumber("010-1234-5678")
			.toiletRegistOpeningHours("09:00-18:00")
			.toiletRegistOpeningHoursDetails("평일만 운영")
			.toiletRegistInstallationYearMonth("2020-01")
			.toiletRegistOwnershipType("공공")
			.toiletRegistWasteDisposalMethod("하수")
			.toiletInfoSafetyFacilityInstallationIsRequired(true)
			.toiletRegistEmergencyBellIsInstalled(true)
			.toiletRegistEmergencyBellLocation("입구 근처")
			.toiletRegistEntranceCctvIsInstalled(true)
			.toiletRegistDiaperChangingTableIsAvailable(true)
			.toiletRegistDiaperChangingTableLocation("메인 홀 근처")
			.toiletRegistMaleToiletsNumber(3)
			.toiletRegistMaleUrinalsNumber(2)
			.toiletRegistMaleDisabledToiletsNumber(1)
			.toiletRegistMaleDisabledUrinalsNumber(1)
			.toiletRegistMaleChildToiletsNumber(1)
			.toiletRegistMaleChildUrinalsNumber(1)
			.toiletRegistFemaleToiletsNumber(3)
			.toiletRegistFemaleDisabledToiletsNumber(1)
			.toiletRegistFemaleChildToiletsNumber(1)
			.userEmail("user@example.com")
			.build();
	}
}
