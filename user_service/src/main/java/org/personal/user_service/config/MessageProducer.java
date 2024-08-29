package org.personal.user_service.config;

import lombok.AllArgsConstructor;
import org.personal.user_service.user.response.ResponseUser;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class MessageProducer {
    private static final String bindingName = "producer-out-0";

    private final StreamBridge streamBridge;

    @Async
    public void send(ResponseUser user) {
        streamBridge.send(
                bindingName,
                MessageBuilder.withPayload(user).build()
        );
    }

}