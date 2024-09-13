package org.personal.info_service.message;

import lombok.AllArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class MessageProducer {

    private final StreamBridge streamBridge;

    @Async
    public void send(String topic, Object message) {
        streamBridge.send(topic, message);
    }

}