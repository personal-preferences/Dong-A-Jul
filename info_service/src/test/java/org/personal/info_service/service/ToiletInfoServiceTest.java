package org.personal.info_service.service;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.personal.info_service.domain.ToiletInfo;
import org.personal.info_service.repository.ToiletInfoRepository;
import org.personal.info_service.request.RequestCreateInfo;
import org.personal.info_service.response.ToiletInfoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class ToiletInfoServiceTest {

    @Autowired
    private ToiletInfoService toiletInfoService;

    @Autowired
    private ToiletInfoRepository  toiletInfoRepository;

    @Test
    @DisplayName("화장실 정보 저장")
    void registToiletInfo(){

        RequestCreateInfo toiletInfo = createTestToiletInfo();

        ToiletInfoResponse savedToiletInfo = toiletInfoService.createToiletInfo(toiletInfo);

        assertThat(savedToiletInfo.toiletInfoId()).isNotNull();
    }

    @Test
    @DisplayName("화장실 정보 수정")
    void modifyToiletInfo(){

        ToiletInfoResponse beforeInfo = toiletInfoService.createToiletInfo(createTestToiletInfo());

        ToiletInfoResponse updateInfo = toiletInfoService.updateToiletInfo(createModifyTestToiletInfo(beforeInfo));

        assertThat(beforeInfo.toiletInfoFemaleToiletsNumber()).isNotEqualTo(updateInfo.toiletInfoFemaleChildToiletsNumber());
    }

    @Test
    @DisplayName("화장실 정보 삭제")
    void deleteToiletInfo(){

        ToiletInfoResponse deleteInfo = toiletInfoService.createToiletInfo(createTestToiletInfo());
        Long deleteId = deleteInfo.toiletLocationId();

        toiletInfoService.deleteToiletinfo(deleteId);

        ToiletInfo deletedInfo = toiletInfoRepository.findByToiletInfoId(deleteInfo.toiletInfoId());

        assertTrue(deletedInfo.isDeleted());
    }


    // 테스트용 ToiletInfoResponse 객체를 생성하는 함수
    public RequestCreateInfo createTestToiletInfo() {
        return RequestCreateInfo.builder()
                .isDeleted(false)
                .toiletInfoManagementAgency("Public Agency")
                .toiletInfoPhoneNumber("123-456-7890")
                .toiletInfoOpeningHours("09:00 - 18:00")
                .toiletInfoOpeningHoursDetails("Open all week except holidays")
                .toiletInfoInstallationYearMonth("2024-07")
                .toiletInfoOwnershipType("Public")
                .toiletInfoWasteDisposalMethod("Sewage")
                .toiletInfoSafetyFacilityInstallationIsRequired(true)
                .toiletInfoEmergencyBellIsInstalled(true)
                .toiletInfoEmergencyBellLocation("Near entrance")
                .toiletInfoEntranceCCTVIsInstalled(true)
                .toiletInfoDiaperChangingTableIsAvailable(true)
                .toiletInfoDiaperChangingTableLocation("Corner near entrance")
                .toiletInfoMaleToiletsNumber(3)
                .toiletInfoMaleUrinalsNumber(4)
                .toiletInfoMaleDisabledToiletsNumber(1)
                .toiletInfoMaleDisabledUrinalsNumber(1)
                .toiletInfoMaleChildToiletsNumber(1)
                .toiletInfoMaleChildUrinalsNumber(1)
                .toiletInfoFemaleToiletsNumber(4)
                .toiletInfoFemaleDisabledToiletsNumber(1)
                .toiletInfoFemaleChildToiletsNumber(1)
                .toiletLocationId(1L)
                .build();
    }

    // 테스트용 ToiletInfoResponse 객체를 생성하는 함수
    public RequestCreateInfo createModifyTestToiletInfo(ToiletInfoResponse toiletInfoResponse) {
        return RequestCreateInfo.builder()
                .toiletInfoId(toiletInfoResponse.toiletInfoId())
                .isDeleted(toiletInfoResponse.isDeleted())
                .toiletInfoManagementAgency(toiletInfoResponse.toiletInfoManagementAgency())
                .toiletInfoPhoneNumber(toiletInfoResponse.toiletInfoPhoneNumber())
                .toiletInfoOpeningHours(toiletInfoResponse.toiletInfoOpeningHours())
                .toiletInfoOpeningHoursDetails(toiletInfoResponse.toiletInfoOpeningHoursDetails())
                .toiletInfoInstallationYearMonth(toiletInfoResponse.toiletInfoInstallationYearMonth())
                .toiletInfoOwnershipType(toiletInfoResponse.toiletInfoOwnershipType())
                .toiletInfoWasteDisposalMethod(toiletInfoResponse.toiletInfoWasteDisposalMethod())
                .toiletInfoSafetyFacilityInstallationIsRequired(toiletInfoResponse.toiletInfoSafetyFacilityInstallationIsRequired())
                .toiletInfoEmergencyBellIsInstalled(toiletInfoResponse.toiletInfoEmergencyBellIsInstalled())
                .toiletInfoEmergencyBellLocation(toiletInfoResponse.toiletInfoEmergencyBellLocation())
                .toiletInfoEntranceCCTVIsInstalled(toiletInfoResponse.toiletInfoEntranceCCTVIsInstalled())
                .toiletInfoDiaperChangingTableIsAvailable(toiletInfoResponse.toiletInfoDiaperChangingTableIsAvailable())
                .toiletInfoDiaperChangingTableLocation(toiletInfoResponse.toiletInfoDiaperChangingTableLocation())
                .toiletInfoMaleToiletsNumber(toiletInfoResponse.toiletInfoMaleToiletsNumber())
                .toiletInfoMaleUrinalsNumber(toiletInfoResponse.toiletInfoMaleUrinalsNumber())
                .toiletInfoMaleDisabledToiletsNumber(toiletInfoResponse.toiletInfoMaleDisabledToiletsNumber())
                .toiletInfoMaleDisabledUrinalsNumber(toiletInfoResponse.toiletInfoMaleDisabledUrinalsNumber())
                .toiletInfoMaleChildToiletsNumber(toiletInfoResponse.toiletInfoMaleChildToiletsNumber())
                .toiletInfoMaleChildUrinalsNumber(toiletInfoResponse.toiletInfoMaleChildUrinalsNumber())
                .toiletInfoFemaleToiletsNumber(toiletInfoResponse.toiletInfoFemaleToiletsNumber() + 10)
                .toiletInfoFemaleDisabledToiletsNumber(toiletInfoResponse.toiletInfoFemaleDisabledToiletsNumber())
                .toiletInfoFemaleChildToiletsNumber(toiletInfoResponse.toiletInfoFemaleChildToiletsNumber())
                .toiletLocationId(toiletInfoResponse.toiletLocationId())
                .build();
    }


}