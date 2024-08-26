package org.personal.user_service.config;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Slf4j
@Service
public class KafkaConsumer implements Consumer<Message<String>> {

    @SneakyThrows
    @Override
    public void accept(Message<String> clusterMessage) {
        log.info("key: {}", clusterMessage.getHeaders().get(KafkaHeaders.RECEIVED_KEY));
        String clusterResponse = clusterMessage.getPayload();
        log.info("payload: {}", clusterResponse);
    }
}