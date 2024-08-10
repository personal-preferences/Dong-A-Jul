package org.personal.registration_service.response;

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
}
