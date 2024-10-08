package org.personal.registration_service.response;

import org.personal.registration_service.domain.ToiletRegist;

import lombok.Builder;

@Builder
public record ToiletRegistResponse(
	Long toiletRegistId,
	String toiletRegistDate,
	Boolean toiletRegistIsApproved,
	String toiletRegistConfirmedDate,
	String toiletRegistToiletName,
	String toiletRegistRoadNameAddress,
	String toiletRegistNumberAddress,
	Double toiletRegistLatitude,
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
	public static ToiletRegistResponse of(ToiletRegist savedToiletRegist) {
		return ToiletRegistResponse.builder()
			.toiletRegistId(savedToiletRegist.getToiletRegistId())
			.toiletRegistDate(savedToiletRegist.getToiletRegistDate())
			.toiletRegistIsApproved(savedToiletRegist.getToiletRegistIsApproved())
			.toiletRegistConfirmedDate(savedToiletRegist.getToiletRegistConfirmedDate())
			.toiletRegistToiletName(savedToiletRegist.getToiletRegistToiletName())
			.toiletRegistRoadNameAddress(savedToiletRegist.getToiletRegistRoadNameAddress())
			.toiletRegistNumberAddress(savedToiletRegist.getToiletRegistNumberAddress())
			.toiletRegistLatitude(savedToiletRegist.getToiletRegistLatitude())
			.toiletRegistLongitude(savedToiletRegist.getToiletRegistLongitude())
			.toiletRegistManagementAgency(savedToiletRegist.getToiletRegistManagementAgency())
			.toiletRegistPhoneNumber(savedToiletRegist.getToiletRegistPhoneNumber())
			.toiletRegistOpeningHours(savedToiletRegist.getToiletRegistOpeningHours())
			.toiletRegistOpeningHoursDetails(savedToiletRegist.getToiletRegistOpeningHoursDetails())
			.toiletRegistInstallationYearMonth(savedToiletRegist.getToiletRegistInstallationYearMonth())
			.toiletRegistOwnershipType(savedToiletRegist.getToiletRegistOwnershipType())
			.toiletRegistWasteDisposalMethod(savedToiletRegist.getToiletRegistWasteDisposalMethod())
			.toiletInfoSafetyFacilityInstallationIsRequired(savedToiletRegist.getToiletInfoSafetyFacilityInstallationIsRequired())
			.toiletRegistEmergencyBellIsInstalled(savedToiletRegist.getToiletRegistEmergencyBellIsInstalled())
			.toiletRegistEmergencyBellLocation(savedToiletRegist.getToiletRegistEmergencyBellLocation())
			.toiletRegistEntranceCctvIsInstalled(savedToiletRegist.getToiletRegistEntranceCctvIsInstalled())
			.toiletRegistDiaperChangingTableIsAvailable(savedToiletRegist.getToiletRegistDiaperChangingTableIsAvailable())
			.toiletRegistDiaperChangingTableLocation(savedToiletRegist.getToiletRegistDiaperChangingTableLocation())
			.toiletRegistMaleToiletsNumber(savedToiletRegist.getToiletRegistMaleToiletsNumber())
			.toiletRegistMaleUrinalsNumber(savedToiletRegist.getToiletRegistMaleUrinalsNumber())
			.toiletRegistMaleDisabledToiletsNumber(savedToiletRegist.getToiletRegistMaleDisabledToiletsNumber())
			.toiletRegistMaleDisabledUrinalsNumber(savedToiletRegist.getToiletRegistMaleDisabledUrinalsNumber())
			.toiletRegistMaleChildToiletsNumber(savedToiletRegist.getToiletRegistMaleChildToiletsNumber())
			.toiletRegistMaleChildUrinalsNumber(savedToiletRegist.getToiletRegistMaleChildUrinalsNumber())
			.toiletRegistFemaleToiletsNumber(savedToiletRegist.getToiletRegistFemaleToiletsNumber())
			.toiletRegistFemaleDisabledToiletsNumber(savedToiletRegist.getToiletRegistFemaleDisabledToiletsNumber())
			.toiletRegistFemaleChildToiletsNumber(savedToiletRegist.getToiletRegistFemaleChildToiletsNumber())
			.userEmail(savedToiletRegist.getUserEmail())
			.build();
	}
}
