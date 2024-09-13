package org.personal.info_service.message;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.personal.info_service.request.RequestCreateInfo;
import org.personal.info_service.request.RequestDeleteInfo;
import org.personal.info_service.response.ToiletInfoResponse;
import org.personal.info_service.service.ToiletInfoService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;

import java.util.function.*;

import java.util.function.Consumer;

@Slf4j
@Configuration
public class KafkaConsumer {

    ObjectMapper objectMapper = new ObjectMapper();
    private final ToiletInfoService toiletInfoService;

    public KafkaConsumer(ToiletInfoService toiletInfoService) {
        this.toiletInfoService = toiletInfoService;
    }

    @Bean
    @SneakyThrows
    public Function<Message<String>, ToiletInfoResponse> createToiletInfo() {
        return message -> {
            try {
                RequestCreateInfo toiletInfo = objectMapper.readValue(message.getPayload(), RequestCreateInfo.class);
                log.info("Processing ToiletInfo: {}", toiletInfo);

                return toiletInfoService.createToiletInfo(toiletInfo);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        };
    }

    @Bean
    @SneakyThrows
    public Consumer<Message<String>> deleteToiletInfo() {
        return message -> {
            try {
                RequestDeleteInfo deleteInfo = objectMapper.readValue(message.getPayload(), RequestDeleteInfo.class);
                log.info("Processing deleteInfo : {}", deleteInfo);

                toiletInfoService.deleteToiletinfo(deleteInfo.locationId());

            } catch (Exception e) {
                e.printStackTrace();
            }
        };
    }

}