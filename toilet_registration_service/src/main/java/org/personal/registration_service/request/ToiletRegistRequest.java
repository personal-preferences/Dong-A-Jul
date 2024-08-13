package org.personal.registration_service.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

public record ToiletRegistRequest(

	String toiletRegistConfirmedDate,
	String toiletRegistToiletName,
	String toiletRegistRoadNameAddress,
	String toiletRegistNumberAddress,

	@NotNull(message = "위도는 필수 값입니다.")
	Double toiletRegistLatitude,

	@NotNull(message = "경도는 필수 값입니다.")
	Double toiletRegistLongitude,

	String toiletRegistManagementAgency,
	String toiletRegistPhoneNumber,
	String toiletRegistOpeningHours,
	String toiletRegistOpeningHoursDetails,
	String toiletRegistInstallationYearMonth,
	String toiletRegistOwnershipType,
	String toiletRegistWasteDisposalMethod,
	Boolean toiletInfoSafetyFacilityInstallationIsRequired,
	Boolean toiletRegistEmergencyBellIsInstalled,
	String toiletRegistEmergencyBellLocation,
	Boolean toiletRegistEntranceCctvIsInstalled,
	Boolean toiletRegistDiaperChangingTableIsAvailable,
	String toiletRegistDiaperChangingTableLocation,
	Integer toiletRegistMaleToiletsNumber,
	Integer toiletRegistMaleUrinalsNumber,
	Integer toiletRegistMaleDisabledToiletsNumber,
	Integer toiletRegistMaleDisabledUrinalsNumber,
	Integer toiletRegistMaleChildToiletsNumber,
	Integer toiletRegistMaleChildUrinalsNumber,
	Integer toiletRegistFemaleToiletsNumber,
	Integer toiletRegistFemaleDisabledToiletsNumber,
	Integer toiletRegistFemaleChildToiletsNumber,
	String userEmail
) {

	@Builder
	public ToiletRegistRequest {

	}
}
