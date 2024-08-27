package org.personal.info_service.response;

import lombok.Builder;

@Builder
public record ToiletInfoResponse(
        Long toiletInfoId,
        boolean isDeleted,
        String toiletInfoManagementAgency,
        String toiletInfoPhoneNumber,
        String toiletInfoOpeningHours,
        String toiletInfoOpeningHoursDetails,
        String toiletInfoInstallationYearMonth,
        String toiletInfoOwnershipType,
        String toiletInfoWasteDisposalMethod,
        boolean toiletInfoSafetyFacilityInstallationIsRequired,
        boolean toiletInfoEmergencyBellIsInstalled,
        String toiletInfoEmergencyBellLocation,
        boolean toiletInfoEntranceCCTVIsInstalled,
        boolean toiletInfoDiaperChangingTableIsAvailable,
        String toiletInfoDiaperChangingTableLocation,
        int toiletInfoMaleToiletsNumber,
        int toiletInfoMaleUrinalsNumber,
        int toiletInfoMaleDisabledToiletsNumber,
        int toiletInfoMaleDisabledUrinalsNumber,
        int toiletInfoMaleChildToiletsNumber,
        int toiletInfoMaleChildUrinalsNumber,
        int toiletInfoFemaleToiletsNumber,
        int toiletInfoFemaleDisabledToiletsNumber,
        int toiletInfoFemaleChildToiletsNumber,
        Long toiletLocationId
) {}
