package org.personal.locations_service.messaging;

import lombok.AllArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class LocationEventProducer {

    private final StreamBridge streamBridge;

    @Async
    public void asyncSend(String topic, String message) {
        streamBridge.send(topic, message);
    }

    public void syncSend(String topic, String message) {
        boolean result = streamBridge.send(topic, message);
        if(!result) {
            throw new RuntimeException("Failed to send message to topic: " + topic + ", message: " + message);
        }
    }
}
