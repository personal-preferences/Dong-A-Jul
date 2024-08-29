package org.personal.user_service.config;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.personal.user_service.user.response.ResponseUser;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Slf4j
@Service
public class KafkaConsumer implements Consumer<Message<String>> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @SneakyThrows
    @Override
//    @KafkaListener(topics = "topic1", groupId = "my-group-id")
    public void accept(Message<String> clusterMessage) {
        log.info("key: {}", clusterMessage.getHeaders().get(KafkaHeaders.RECEIVED_KEY));
        ResponseUser clusterResponse =  objectMapper.readValue(clusterMessage.getPayload(), ResponseUser.class);
        log.info("payload: {}", clusterResponse);
    }
}