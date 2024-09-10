package org.personal.locations_service.message;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.personal.locations_service.domain.Location;
import org.personal.locations_service.message.dto.LocationDeletedEvent;
import org.personal.locations_service.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.util.function.Consumer;

@SpringBootTest
@Import(TestChannelBinderConfiguration.class)

class LocationEventConsumerTest {

    @Autowired
    private LocationEventConsumer locationEventConsumer;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("화장실 삭제 이벤트 소비")
    void locationDeletedEvent() throws Exception {
        // given
        Location location = Location.builder()
                .name("홍대 화장실")
                .roadAddress("서울 마포구 서교동 1길")
                .jibunAddress("서울 마포구 동교동 150-1")
                .latitude(10.23f)
                .longitude(32.99f)
                .build();
        locationRepository.save(location);

        LocationDeletedEvent locationDeletedEvent = LocationDeletedEvent.builder()
                .locationId(location.getId())
                .build();

        // when
        String payload = objectMapper.writeValueAsString(locationDeletedEvent);
        Message<String> message = MessageBuilder.withPayload(payload).build();

        // then
        Consumer<Message<String>> consumer = locationEventConsumer.input();
        consumer.accept(message);
    }

}