package org.personal.info_service.service;

import org.personal.info_service.domain.ToiletInfo;
import org.personal.info_service.repository.ToiletInfoRepository;
import org.personal.info_service.request.RequestCreateInfo;
import org.personal.info_service.response.ToiletInfoResponse;
import org.springframework.stereotype.Service;

@Service
public class ToiletInfoServiceImpl implements ToiletInfoService {

    private final ToiletInfoRepository toiletInfoRepository;

    public ToiletInfoServiceImpl(ToiletInfoRepository toiletInfoRepository) {
        this.toiletInfoRepository = toiletInfoRepository;
    }

    @Override
    public ToiletInfoResponse createToiletInfo(RequestCreateInfo toiletInfo) {

        ToiletInfo requestInfo = convertRequestCreateInfoToToiletInfo(toiletInfo);

        toiletInfoRepository.save(requestInfo);

        return convertToiletInfoToResponse(requestInfo);
    }

    @Override
    public ToiletInfoResponse updateToiletInfo(RequestCreateInfo modifyToiletInfo) {

        ToiletInfo savedInfo = toiletInfoRepository.findByToiletLocationId(modifyToiletInfo.toiletLocationId());

        if(savedInfo == null){
            throw new IllegalArgumentException("화장실 정보가 존재하지 않습니다.");
        }

        ToiletInfo updateInfo = convertRequestCreateInfoToToiletInfo(modifyToiletInfo);
        updateInfo.setToiletInfoId(savedInfo.getToiletInfoId());
        toiletInfoRepository.save(updateInfo);

        return convertToiletInfoToResponse(updateInfo);
    }

    @Override
    public void deleteToiletinfo(Long locationId) {
        ToiletInfo savedInfo = toiletInfoRepository.findByToiletLocationId(locationId);

        if(savedInfo == null){
            throw new IllegalArgumentException();
        }

        savedInfo.setDeleted(true);
        toiletInfoRepository.save(savedInfo);
    }

    // ToiletInfoResponse를 ToiletInfo로 변환하는 메서드
    private ToiletInfo convertRequestCreateInfoToToiletInfo(RequestCreateInfo response) {
        return ToiletInfo.builder()
                .isDeleted(response.isDeleted())
                .toiletInfoManagementAgency(response.toiletInfoManagementAgency())
                .toiletInfoPhoneNumber(response.toiletInfoPhoneNumber())
                .toiletInfoOpeningHours(response.toiletInfoOpeningHours())
                .toiletInfoOpeningHoursDetails(response.toiletInfoOpeningHoursDetails())
                .toiletInfoInstallationYearMonth(response.toiletInfoInstallationYearMonth())
                .toiletInfoOwnershipType(response.toiletInfoOwnershipType())
                .toiletInfoWasteDisposalMethod(response.toiletInfoWasteDisposalMethod())
                .toiletInfoSafetyFacilityInstallationIsRequired(response.toiletInfoSafetyFacilityInstallationIsRequired())
                .toiletInfoEmergencyBellIsInstalled(response.toiletInfoEmergencyBellIsInstalled())
                .toiletInfoEmergencyBellLocation(response.toiletInfoEmergencyBellLocation())
                .toiletInfoEntranceCctvIsInstalled(response.toiletInfoEntranceCCTVIsInstalled())
                .toiletInfoDiaperChangingTableIsAvailable(response.toiletInfoDiaperChangingTableIsAvailable())
                .toiletInfoDiaperChangingTableLocation(response.toiletInfoDiaperChangingTableLocation())
                .toiletInfoMaleToiletsNumber(response.toiletInfoMaleToiletsNumber())
                .toiletInfoMaleUrinalsNumber(response.toiletInfoMaleUrinalsNumber())
                .toiletInfoMaleDisabledToiletsNumber(response.toiletInfoMaleDisabledToiletsNumber())
                .toiletInfoMaleDisabledUrinalsNumber(response.toiletInfoMaleDisabledUrinalsNumber())
                .toiletInfoMaleChildToiletsNumber(response.toiletInfoMaleChildToiletsNumber())
                .toiletInfoMaleChildUrinalsNumber(response.toiletInfoMaleChildUrinalsNumber())
                .toiletInfoFemaleToiletsNumber(response.toiletInfoFemaleToiletsNumber())
                .toiletInfoFemaleDisabledToiletsNumber(response.toiletInfoFemaleDisabledToiletsNumber())
                .toiletInfoFemaleChildToiletsNumber(response.toiletInfoFemaleChildToiletsNumber())
                .toiletLocationId(response.toiletLocationId())
                .build();
    }


    // ToiletInfo를 ToiletInfoResponse로 변환하는 메서드
    private ToiletInfoResponse convertToiletInfoToResponse(ToiletInfo toiletInfo) {
        return ToiletInfoResponse.builder()
                .toiletInfoId(toiletInfo.getToiletInfoId())
                .isDeleted(toiletInfo.isDeleted())
                .toiletInfoManagementAgency(toiletInfo.getToiletInfoManagementAgency())
                .toiletInfoPhoneNumber(toiletInfo.getToiletInfoPhoneNumber())
                .toiletInfoOpeningHours(toiletInfo.getToiletInfoOpeningHours())
                .toiletInfoOpeningHoursDetails(toiletInfo.getToiletInfoOpeningHoursDetails())
                .toiletInfoInstallationYearMonth(toiletInfo.getToiletInfoInstallationYearMonth())
                .toiletInfoOwnershipType(toiletInfo.getToiletInfoOwnershipType())
                .toiletInfoWasteDisposalMethod(toiletInfo.getToiletInfoWasteDisposalMethod())
                .toiletInfoSafetyFacilityInstallationIsRequired(toiletInfo.isToiletInfoSafetyFacilityInstallationIsRequired())
                .toiletInfoEmergencyBellIsInstalled(toiletInfo.isToiletInfoEmergencyBellIsInstalled())
                .toiletInfoEmergencyBellLocation(toiletInfo.getToiletInfoEmergencyBellLocation())
                .toiletInfoEntranceCCTVIsInstalled(toiletInfo.isToiletInfoEntranceCctvIsInstalled())
                .toiletInfoDiaperChangingTableIsAvailable(toiletInfo.isToiletInfoDiaperChangingTableIsAvailable())
                .toiletInfoDiaperChangingTableLocation(toiletInfo.getToiletInfoDiaperChangingTableLocation())
                .toiletInfoMaleToiletsNumber(toiletInfo.getToiletInfoMaleToiletsNumber())
                .toiletInfoMaleUrinalsNumber(toiletInfo.getToiletInfoMaleUrinalsNumber())
                .toiletInfoMaleDisabledToiletsNumber(toiletInfo.getToiletInfoMaleDisabledToiletsNumber())
                .toiletInfoMaleDisabledUrinalsNumber(toiletInfo.getToiletInfoMaleDisabledUrinalsNumber())
                .toiletInfoMaleChildToiletsNumber(toiletInfo.getToiletInfoMaleChildToiletsNumber())
                .toiletInfoMaleChildUrinalsNumber(toiletInfo.getToiletInfoMaleChildUrinalsNumber())
                .toiletInfoFemaleToiletsNumber(toiletInfo.getToiletInfoFemaleToiletsNumber())
                .toiletInfoFemaleDisabledToiletsNumber(toiletInfo.getToiletInfoFemaleDisabledToiletsNumber())
                .toiletInfoFemaleChildToiletsNumber(toiletInfo.getToiletInfoFemaleChildToiletsNumber())
                .toiletLocationId(toiletInfo.getToiletLocationId())
                .build();
    }
}
