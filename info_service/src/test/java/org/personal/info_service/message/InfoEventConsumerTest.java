package org.personal.info_service.message;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.personal.info_service.repository.ToiletInfoRepository;
import org.personal.info_service.request.RequestCreateInfo;
import org.personal.info_service.request.RequestDeleteInfo;
import org.personal.info_service.response.ToiletInfoResponse;
import org.personal.info_service.service.ToiletInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.util.function.Consumer;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Import(TestChannelBinderConfiguration.class)

class InfoEventConsumerTest {

    @Autowired
    private InfoEventConsumer infoEventConsumer;

    @Autowired
    private ToiletInfoService toiletInfoService;

    @Autowired
    private ObjectMapper objectMapper;

    public final static Long TEST_ID = -1L;

    @Test
    @DisplayName("화장실 정보 등록 이벤트 소비")
    void createToiletInfoEvent() throws Exception {

        RequestCreateInfo toiletInfo = createTestToiletInfo();

        String payload = objectMapper.writeValueAsString(toiletInfo);
        Message<String> message = MessageBuilder.withPayload(payload).build();

        Function<Message<String>, ToiletInfoResponse> consumer = infoEventConsumer.createToiletInfo();
        consumer.apply(message);
    }

    @Test
    @DisplayName("화장실 정보 삭제 이벤트 소비")
    void deleteToiletInfoEvent() throws Exception {

        ToiletInfoResponse res = toiletInfoService.createToiletInfo(createTestToiletInfo());
        RequestDeleteInfo deleteInfo = RequestDeleteInfo.builder()
                .locationId(res.toiletLocationId())
                .build();

        String payload = objectMapper.writeValueAsString(deleteInfo);
        Message<String> message = MessageBuilder.withPayload(payload).build();

        Consumer<Message<String>> consumer = infoEventConsumer.deleteToiletInfo();
        consumer.accept(message);
    }

    // 테스트용 RequestCreateInfo 객체 생성
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
                .toiletLocationId(TEST_ID)
                .build();
    }

}