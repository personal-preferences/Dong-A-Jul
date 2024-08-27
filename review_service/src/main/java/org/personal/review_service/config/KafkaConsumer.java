package org.personal.review_service.config;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.personal.review_service.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Slf4j
@Service
public class KafkaConsumer implements Consumer<Message<String>> {

    private final ReviewService reviewService;

    @Autowired
    public KafkaConsumer(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @SneakyThrows
    @Override
    public void accept(Message<String> clusterMessage) {
        log.info("key: {}", clusterMessage.getHeaders().get(KafkaHeaders.RECEIVED_KEY));
        String clusterResponse = clusterMessage.getPayload();
        log.info("payload: {}", clusterResponse);


        try {
            // 메시지에서 리뷰 ID를 추출하여 삭제
            Long reviewId = Long.parseLong(clusterResponse);
            reviewService.deleteReviewByReviewId(reviewId);
            log.info("Deleted review with ID: {}", reviewId);
        } catch (NumberFormatException e) {
            log.error("Failed to parse review ID from message: {}", clusterResponse, e);
        } catch (IllegalArgumentException e) {
            log.error("Error during review deletion: {}", e.getMessage());
        }
    }
}