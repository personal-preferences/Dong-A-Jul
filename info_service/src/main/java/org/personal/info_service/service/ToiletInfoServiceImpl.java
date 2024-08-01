package org.personal.info_service.service;

import org.personal.info_service.domain.ToiletInfo;
import org.personal.info_service.repository.ToiletInfoRepository;
import org.personal.info_service.response.ToiletInfoResponse;
import org.springframework.stereotype.Service;

@Service
public class ToiletInfoServiceImpl implements ToiletInfoService {

    private final ToiletInfoRepository toiletInfoRepository;

    public ToiletInfoServiceImpl(ToiletInfoRepository toiletInfoRepository) {
        this.toiletInfoRepository = toiletInfoRepository;
    }

    @Override
    public ToiletInfoResponse createToiletInfo(ToiletInfoResponse toiletInfo) {

        ToiletInfo requestInfo = convertToiletInfoResponseToToiletInfo(toiletInfo);

        toiletInfoRepository.save(requestInfo);

        return convertToiletInfoToResponse(requestInfo);
    }

    // ToiletInfoResponse를 ToiletInfo로 변환하는 메서드
    private ToiletInfo convertToiletInfoResponseToToiletInfo(ToiletInfoResponse response) {
        ToiletInfo toiletInfo = new ToiletInfo();
        toiletInfo.setToiletInfoId(response.toiletInfoId());
        toiletInfo.setDeleted(response.isDeleted());
        toiletInfo.setToiletInfoManagementAgency(response.toiletInfoManagementAgency());
        toiletInfo.setToiletInfoPhoneNumber(response.toiletInfoPhoneNumber());
        toiletInfo.setToiletInfoOpeningHours(response.toiletInfoOpeningHours());
        toiletInfo.setToiletInfoOpeningHoursDetails(response.toiletInfoOpeningHoursDetails());
        toiletInfo.setToiletInfoInstallationYearMonth(response.toiletInfoInstallationYearMonth());
        toiletInfo.setToiletInfoOwnershipType(response.toiletInfoOwnershipType());
        toiletInfo.setToiletInfoWasteDisposalMethod(response.toiletInfoWasteDisposalMethod());
        toiletInfo.setToiletInfoSafetyFacilityInstallationIsRequired(response.toiletInfoSafetyFacilityInstallationIsRequired());
        toiletInfo.setToiletInfoEmergencyBellIsInstalled(response.toiletInfoEmergencyBellIsInstalled());
        toiletInfo.setToiletInfoEmergencyBellLocation(response.toiletInfoEmergencyBellLocation());
        toiletInfo.setToiletInfoEntranceCctvIsInstalled(response.toiletInfoEntranceCCTVIsInstalled());
        toiletInfo.setToiletInfoDiaperChangingTableIsAvailable(response.toiletInfoDiaperChangingTableIsAvailable());
        toiletInfo.setToiletInfoDiaperChangingTableLocation(response.toiletInfoDiaperChangingTableLocation());
        toiletInfo.setToiletInfoMaleToiletsNumber(response.toiletInfoMaleToiletsNumber());
        toiletInfo.setToiletInfoMaleUrinalsNumber(response.toiletInfoMaleUrinalsNumber());
        toiletInfo.setToiletInfoMaleDisabledToiletsNumber(response.toiletInfoMaleDisabledToiletsNumber());
        toiletInfo.setToiletInfoMaleDisabledUrinalsNumber(response.toiletInfoMaleDisabledUrinalsNumber());
        toiletInfo.setToiletInfoMaleChildToiletsNumber(response.toiletInfoMaleChildToiletsNumber());
        toiletInfo.setToiletInfoMaleChildUrinalsNumber(response.toiletInfoMaleChildUrinalsNumber());
        toiletInfo.setToiletInfoFemaleToiletsNumber(response.toiletInfoFemaleToiletsNumber());
        toiletInfo.setToiletInfoFemaleDisabledToiletsNumber(response.toiletInfoFemaleDisabledToiletsNumber());
        toiletInfo.setToiletInfoFemaleChildToiletsNumber(response.toiletInfoFemaleChildToiletsNumber());
        toiletInfo.setToiletLocationId(response.toiletLocationId());

        return toiletInfo;
    }

    // ToiletInfo를 ToiletInfoResponse로 변환하는 메서드
    private ToiletInfoResponse convertToiletInfoToResponse(ToiletInfo toiletInfo) {
        return new ToiletInfoResponse(
                toiletInfo.getToiletInfoId(),
                toiletInfo.isDeleted(),
                toiletInfo.getToiletInfoManagementAgency(),
                toiletInfo.getToiletInfoPhoneNumber(),
                toiletInfo.getToiletInfoOpeningHours(),
                toiletInfo.getToiletInfoOpeningHoursDetails(),
                toiletInfo.getToiletInfoInstallationYearMonth(),
                toiletInfo.getToiletInfoOwnershipType(),
                toiletInfo.getToiletInfoWasteDisposalMethod(),
                toiletInfo.isToiletInfoSafetyFacilityInstallationIsRequired(),
                toiletInfo.isToiletInfoEmergencyBellIsInstalled(),
                toiletInfo.getToiletInfoEmergencyBellLocation(),
                toiletInfo.isToiletInfoEntranceCctvIsInstalled(),
                toiletInfo.isToiletInfoDiaperChangingTableIsAvailable(),
                toiletInfo.getToiletInfoDiaperChangingTableLocation(),
                toiletInfo.getToiletInfoMaleToiletsNumber(),
                toiletInfo.getToiletInfoMaleUrinalsNumber(),
                toiletInfo.getToiletInfoMaleDisabledToiletsNumber(),
                toiletInfo.getToiletInfoMaleDisabledUrinalsNumber(),
                toiletInfo.getToiletInfoMaleChildToiletsNumber(),
                toiletInfo.getToiletInfoMaleChildUrinalsNumber(),
                toiletInfo.getToiletInfoFemaleToiletsNumber(),
                toiletInfo.getToiletInfoFemaleDisabledToiletsNumber(),
                toiletInfo.getToiletInfoFemaleChildToiletsNumber(),
                toiletInfo.getToiletLocationId()
        );
    }
}
