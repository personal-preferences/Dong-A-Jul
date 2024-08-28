package org.personal.locations_service.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.personal.locations_service.request.LocationCreate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;

import java.util.function.Consumer;

@Slf4j
@Configuration
public class LocationEventConsumer {

    ObjectMapper objectMapper = new ObjectMapper();

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
            try {
                LocationCreate locationCreate = objectMapper.readValue(message.getPayload(), LocationCreate.class);
                log.info("[Async] 객체 변환: {}", locationCreate);
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
    }
}
