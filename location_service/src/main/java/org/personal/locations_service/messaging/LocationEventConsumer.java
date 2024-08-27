package org.personal.locations_service.messaging;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;

import java.util.function.Consumer;

@Slf4j
@Configuration
public class LocationEventConsumer {

    @Bean
    public Consumer<Message<String>> input() {
        return message -> {
            log.error("[Sync] Processing message: {}", message);
            log.info("[Sync] Processed payload: {}", message.getPayload());
        };
    }

    @Bean
    public Consumer<Message<String>> inputAsync() {
        return message -> {
            log.error("[Async] Processing message: {}", message);
            log.info("[Async] Processed payload: {}", message.getPayload());
        };
    }
}
