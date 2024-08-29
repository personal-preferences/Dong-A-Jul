package org.personal.registration_service.request;

import org.personal.registration_service.domain.ToiletRegist;

import lombok.Builder;

@Builder
public record ToiletInformationRequest(
	String toiletInfoManagementAgency,
	String toiletInfoPhoneNumber,
	String toiletInfoOpeningHours,
	String toiletInfoOpeningHoursDetails,
	String toiletInfoInstallationYearMonth,
	String toiletInfoOwnershipType,
	String toiletInfoWasteDisposalMethod,
	Boolean toiletInfoSafetyFacilityInstallationIsRequired,
	Boolean toiletInfoEmergencyBellIsInstalled,
	String toiletInfoEmergencyBellLocation,
	Boolean toiletInfoEntranceCCTVIsInstalled,
	Boolean toiletInfoDiaperChangingTableIsAvailable,
	String toiletInfoDiaperChangingTableLocation,
	Integer toiletInfoMaleToiletsNumber,
	Integer toiletInfoMaleUrinalsNumber,
	Integer toiletInfoMaleDisabledToiletsNumber,
	Integer toiletInfoMaleDisabledUrinalsNumber,
	Integer toiletInfoMaleChildToiletsNumber,
	Integer toiletInfoMaleChildUrinalsNumber,
	Integer toiletInfoFemaleToiletsNumber,
	Integer toiletInfoFemaleDisabledToiletsNumber,
	Integer toiletInfoFemaleChildToiletsNumber,
	Long toiletLocationId
) {
	public static ToiletInformationRequest of(Long toiletLocationId, ToiletRegist toiletRegist){
		return ToiletInformationRequest.builder()
			.toiletInfoManagementAgency(toiletRegist.getToiletRegistManagementAgency())
			.toiletInfoPhoneNumber(toiletRegist.getToiletRegistPhoneNumber())
			.toiletInfoOpeningHours(toiletRegist.getToiletRegistOpeningHours())
			.toiletInfoOpeningHoursDetails(toiletRegist.getToiletRegistOpeningHoursDetails())
			.toiletInfoInstallationYearMonth(toiletRegist.getToiletRegistInstallationYearMonth())
			.toiletInfoOwnershipType(toiletRegist.getToiletRegistOwnershipType())
			.toiletInfoWasteDisposalMethod(toiletRegist.getToiletRegistWasteDisposalMethod())
			.toiletInfoSafetyFacilityInstallationIsRequired(toiletRegist.getToiletInfoSafetyFacilityInstallationIsRequired())
			.toiletInfoEmergencyBellIsInstalled(toiletRegist.getToiletRegistEmergencyBellIsInstalled())
			.toiletInfoEmergencyBellLocation(toiletRegist.getToiletRegistEmergencyBellLocation())
			.toiletInfoEntranceCCTVIsInstalled(toiletRegist.getToiletRegistEntranceCctvIsInstalled())
			.toiletInfoDiaperChangingTableIsAvailable(toiletRegist.getToiletRegistDiaperChangingTableIsAvailable())
			.toiletInfoDiaperChangingTableLocation(toiletRegist.getToiletRegistDiaperChangingTableLocation())
			.toiletInfoMaleToiletsNumber(toiletRegist.getToiletRegistMaleToiletsNumber())
			.toiletInfoMaleUrinalsNumber(toiletRegist.getToiletRegistMaleUrinalsNumber())
			.toiletInfoMaleDisabledToiletsNumber(toiletRegist.getToiletRegistMaleDisabledToiletsNumber())
			.toiletInfoMaleDisabledUrinalsNumber(toiletRegist.getToiletRegistMaleDisabledUrinalsNumber())
			.toiletInfoMaleChildToiletsNumber(toiletRegist.getToiletRegistMaleChildToiletsNumber())
			.toiletInfoMaleChildUrinalsNumber(toiletRegist.getToiletRegistMaleChildUrinalsNumber())
			.toiletInfoFemaleToiletsNumber(toiletRegist.getToiletRegistFemaleToiletsNumber())
			.toiletInfoFemaleDisabledToiletsNumber(toiletRegist.getToiletRegistFemaleDisabledToiletsNumber())
			.toiletInfoFemaleChildToiletsNumber(toiletRegist.getToiletRegistFemaleChildToiletsNumber())
			.toiletLocationId(toiletLocationId)
			.build();
	}
}