package org.personal.registration_service.service.impl;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.personal.registration_service.common.Constants.*;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
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
import org.personal.registration_service.response.ToiletRegistResponse;

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

	@Test
	public void 화장실등록조회_성공() {
		// given
		ToiletRegist mockToiletRegist = ToiletRegist.builder()
			.toiletRegistId(1L)
			.toiletRegistDate("2023-08-12")
			.toiletRegistIsApproved(true)
			.toiletRegistConfirmedDate("2023-08-13")
			.toiletRegistToiletName("서울역 화장실")
			.toiletRegistRoadNameAddress("서울시 용산구 한강대로 405")
			.toiletRegistNumberAddress("서울시 용산구 남대문로 20")
			.toiletRegistLatitude(37.5551)
			.toiletRegistLongitude(126.9707)
			.toiletRegistManagementAgency("용산구청")
			.toiletRegistPhoneNumber("02-123-4567")
			.toiletRegistOpeningHours("24시간")
			.toiletRegistOpeningHoursDetails("연중무휴")
			.toiletRegistInstallationYearMonth("2020-05")
			.toiletRegistOwnershipType("공공")
			.toiletRegistWasteDisposalMethod("정화조")
			.toiletInfoSafetyFacilityInstallationIsRequired(true)
			.toiletRegistEmergencyBellIsInstalled(true)
			.toiletRegistEmergencyBellLocation("남자화장실 입구")
			.toiletRegistEntranceCctvIsInstalled(true)
			.toiletRegistDiaperChangingTableIsAvailable(true)
			.toiletRegistDiaperChangingTableLocation("여자화장실 입구")
			.toiletRegistMaleToiletsNumber(5)
			.toiletRegistMaleUrinalsNumber(10)
			.toiletRegistMaleDisabledToiletsNumber(1)
			.toiletRegistMaleDisabledUrinalsNumber(2)
			.toiletRegistMaleChildToiletsNumber(1)
			.toiletRegistMaleChildUrinalsNumber(2)
			.toiletRegistFemaleToiletsNumber(8)
			.toiletRegistFemaleDisabledToiletsNumber(2)
			.toiletRegistFemaleChildToiletsNumber(2)
			.userEmail("user@example.com")
			.build();

		doReturn(Optional.of(mockToiletRegist)).when(toiletRegistRepository).findBytoiletRegistId(1L);

		// when
		ToiletRegistResponse response = target.getToiletRegist(1L);

		// then
		assertThat(response).isNotNull();
		assertThat(response.toiletRegistId()).isEqualTo(1L);
		assertThat(response.toiletRegistToiletName()).isEqualTo("서울역 화장실");
		assertThat(response.toiletRegistRoadNameAddress()).isEqualTo("서울시 용산구 한강대로 405");
		assertThat(response.toiletRegistNumberAddress()).isEqualTo("서울시 용산구 남대문로 20");
		assertThat(response.toiletRegistLatitude()).isEqualTo(37.5551);
		assertThat(response.toiletRegistLongitude()).isEqualTo(126.9707);
		assertThat(response.toiletRegistManagementAgency()).isEqualTo("용산구청");
		assertThat(response.toiletRegistPhoneNumber()).isEqualTo("02-123-4567");
		assertThat(response.toiletRegistOpeningHours()).isEqualTo("24시간");
		assertThat(response.toiletRegistOpeningHoursDetails()).isEqualTo("연중무휴");
		assertThat(response.toiletRegistInstallationYearMonth()).isEqualTo("2020-05");
		assertThat(response.toiletRegistOwnershipType()).isEqualTo("공공");
		assertThat(response.toiletRegistWasteDisposalMethod()).isEqualTo("정화조");
		assertThat(response.toiletInfoSafetyFacilityInstallationIsRequired()).isTrue();
		assertThat(response.toiletRegistEmergencyBellIsInstalled()).isTrue();
		assertThat(response.toiletRegistEmergencyBellLocation()).isEqualTo("남자화장실 입구");
		assertThat(response.toiletRegistEntranceCctvIsInstalled()).isTrue();
		assertThat(response.toiletRegistDiaperChangingTableIsAvailable()).isTrue();
		assertThat(response.toiletRegistDiaperChangingTableLocation()).isEqualTo("여자화장실 입구");
		assertThat(response.toiletRegistMaleToiletsNumber()).isEqualTo(5);
		assertThat(response.toiletRegistMaleUrinalsNumber()).isEqualTo(10);
		assertThat(response.toiletRegistMaleDisabledToiletsNumber()).isEqualTo(1);
		assertThat(response.toiletRegistMaleDisabledUrinalsNumber()).isEqualTo(2);
		assertThat(response.toiletRegistMaleChildToiletsNumber()).isEqualTo(1);
		assertThat(response.toiletRegistMaleChildUrinalsNumber()).isEqualTo(2);
		assertThat(response.toiletRegistFemaleToiletsNumber()).isEqualTo(8);
		assertThat(response.toiletRegistFemaleDisabledToiletsNumber()).isEqualTo(2);
		assertThat(response.toiletRegistFemaleChildToiletsNumber()).isEqualTo(2);
		assertThat(response.userEmail()).isEqualTo("user@example.com");
	}

	@Test
	public void 화장실등록조회실패_등록된화장실없음() {
		// given
		doReturn(Optional.empty()).when(toiletRegistRepository).findBytoiletRegistId(1L);

		// when
		ToiletRegistException exception = assertThrows(ToiletRegistException.class, () -> {
			target.getToiletRegist(1L);
		});

		// then
		assertThat(exception).isNotNull();
		assertThat(exception.getErrorResult()).isEqualTo(ToiletRegistErrorResult.ENTITY_NOT_FOUND);
	}

	private ToiletRegistApproveRequest requestReject(){
		return ToiletRegistApproveRequest.builder()
			.toiletRegistId(1L)
			.isApproved(false)
			.build();
	}

	private ToiletRegistApproveRequest requestApprove(){
		return ToiletRegistApproveRequest.builder()
			.toiletRegistId(1L)
			.isApproved(true)
			.build();
	}

}