package org.personal.user_service.config;

import lombok.AllArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class MessageProducer {
    private static final String bindingName = "producer-out-0";

    private final StreamBridge streamBridge;

    @Async
    public void send() {
//        streamBridge.send(bindingName, MessageBuilder
//                .withPayload(payload)
//                .setHeader(KafkaHeaders.KEY, key)
//                .build());
        streamBridge.send(bindingName,"하이요");
    }

}