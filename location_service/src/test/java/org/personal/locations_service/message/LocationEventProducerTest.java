package org.personal.locations_service.message;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.personal.locations_service.domain.Location;
import org.personal.locations_service.message.dto.LocationDeletedEvent;
import org.personal.locations_service.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.binder.test.InputDestination;
import org.springframework.cloud.stream.binder.test.OutputDestination;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.Message;

import java.io.IOException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Import(TestChannelBinderConfiguration.class)
class LocationEventProducerTest {

    @Autowired
    private OutputDestination output;

    @Autowired
    private LocationEventProducer producer;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("화장실 삭제 이벤트 발행")
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

        String topic = "toilet-location-deleted-test";

        // when
        producer.send(topic, locationDeletedEvent);

        // then
        Message<byte[]> received = output.receive(1000, topic);
        assertNotNull(received);

        LocationDeletedEvent payload = objectMapper.readValue(received.getPayload(), LocationDeletedEvent.class);
        assertEquals(location.getId(), payload.locationId());
        assertEquals(locationDeletedEvent, payload);
        assertEquals("location-service", received.getHeaders().get("source"));
    }
}