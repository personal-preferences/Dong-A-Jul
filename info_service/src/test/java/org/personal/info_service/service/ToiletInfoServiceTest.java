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
        return ToiletInfoResponse.builder()
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

}