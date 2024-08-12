package org.personal.registration_service.response;

import org.personal.registration_service.domain.ToiletRegist;

import lombok.Builder;

@Builder
public record ToiletRegistResponse(
	Long toiletRegistId,
	String toiletRegistDate,
	Boolean toiletRegistIsApproved,
	// String toiletRegistConfirmedDate,
	// String toiletRegistToiletName,
	// String toiletRegistRoadNameAddress,
	// String toiletRegistNumberAddress,
	double toiletRegistLatitude,
	double toiletRegistLongitude
	// String toiletRegistManagementAgency,
	// String toiletRegistPhoneNumber,
	// String toiletRegistOpeningHours,
	// String toiletRegistOpeningHoursDetails,
	// String toiletRegistInstallationYearMonth,
	// String toiletRegistOwnershipType,
	// String toiletRegistWasteDisposalMethod,
	// Boolean toiletInfoSafetyFacilityInstallationIsRequired,
	// Boolean toiletRegistEmergencyBellIsInstalled,
	// String toiletRegistEmergencyBellLocation,
	// Boolean toiletRegistEntranceCctvIsInstalled,
	// Boolean toiletRegistDiaperChangingTableIsAvailable,
	// String toiletRegistDiaperChangingTableLocation,
	// Integer toiletRegistMaleToiletsNumber,
	// Integer toiletRegistMaleUrinalsNumber,
	// Integer toiletRegistMaleDisabledToiletsNumber,
	// Integer toiletRegistMaleDisabledUrinalsNumber,
	// Integer toiletRegistMaleChildToiletsNumber,
	// Integer toiletRegistMaleChildUrinalsNumber,
	// Integer toiletRegistFemaleToiletsNumber,
	// Integer toiletRegistFemaleDisabledToiletsNumber,
	// Integer toiletRegistFemaleChildToiletsNumber,
	// String userEmail
) {
	public static ToiletRegistResponse of(ToiletRegist savedToiletRegist) {
		return ToiletRegistResponse.builder()
			.toiletRegistId(savedToiletRegist.getToiletRegistId())
			.toiletRegistDate(savedToiletRegist.getToiletRegistDate())
			.toiletRegistIsApproved(savedToiletRegist.getToiletRegistIsApproved())
			.toiletRegistLatitude(savedToiletRegist.getToiletRegistLatitude())
			.toiletRegistLongitude(savedToiletRegist.getToiletRegistLongitude())
			.build();
	}
}
