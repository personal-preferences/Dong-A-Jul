package org.personal.locations_service.messaging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.KafkaException;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class LocationEventProducer {

    private final StreamBridge streamBridge;
    private static final String SERVICE_NAME = "location-service";

    public void send(String topic, Object payload) {
        Message<Object> message = createMessage(payload);

        try {
            streamBridge.send(topic, message);
        } catch (KafkaException e){
            log.error("[{}] 메시지 발행 실패하였습니다. {}", topic, e.getMessage());
        }
    }

    private Message<Object> createMessage(Object payload) {
        return MessageBuilder
                .withPayload(payload)
                .setHeader("source", SERVICE_NAME)
                .build();
    }
}
