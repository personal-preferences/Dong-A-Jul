package org.personal.info_service.service;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.personal.info_service.domain.ToiletInfo;
import org.personal.info_service.response.ToiletInfoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class ToiletInfoServiceTest {

    @Autowired
    ToiletInfoService toiletInfoService;

    @Test
    @DisplayName("화장실 정보 저장")
    void registToiletInfo(){

        ToiletInfoResponse toiletInfo = createTestToiletInfo();

        ToiletInfoResponse savedToiletInfo = toiletInfoService.createToiletInfo(toiletInfo);

        assertThat(savedToiletInfo.toiletInfoId()).isNotNull();
    }

    // 테스트용 ToiletInfoResponse 객체를 생성하는 함수
    public ToiletInfoResponse createTestToiletInfo() {
        return new ToiletInfoResponse(
                null, // toiletInfoId는 null로 설정 (자동 생성될 것으로 가정)
                false, // isDeleted
                "Public Agency", // toiletInfoManagementAgency
                "123-456-7890", // toiletInfoPhoneNumber
                "09:00 - 18:00", // toiletInfoOpeningHours
                "Open all week except holidays", // toiletInfoOpeningHoursDetails
                "2024-07", // toiletInfoInstallationYearMonth
                "Public", // toiletInfoOwnershipType
                "Sewage", // toiletInfoWasteDisposalMethod
                true, // toiletInfoSafetyFacilityInstallationIsRequired
                true, // toiletInfoEmergencyBellIsInstalled
                "Near entrance", // toiletInfoEmergencyBellLocation
                true, // toiletInfoEntranceCCTVIsInstalled
                true, // toiletInfoDiaperChangingTableIsAvailable
                "Corner near entrance", // toiletInfoDiaperChangingTableLocation
                3, // toiletInfoMaleToiletsNumber
                4, // toiletInfoMaleUrinalsNumber
                1, // toiletInfoMaleDisabledToiletsNumber
                1, // toiletInfoMaleDisabledUrinalsNumber
                1, // toiletInfoMaleChildToiletsNumber
                1, // toiletInfoMaleChildUrinalsNumber
                4, // toiletInfoFemaleToiletsNumber
                1, // toiletInfoFemaleDisabledToiletsNumber
                1, // toiletInfoFemaleChildToiletsNumber
                1L // toiletLocationId
        );
    }

}