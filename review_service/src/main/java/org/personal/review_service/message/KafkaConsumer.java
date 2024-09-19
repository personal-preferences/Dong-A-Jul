package org.personal.review_service.message;

import lombok.extern.slf4j.Slf4j;
import org.personal.review_service.domain.Review;
import org.personal.review_service.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;
import org.springframework.cloud.stream.annotation.StreamListener;

import java.util.Map;

@Slf4j
@Service
public class KafkaConsumer {

    private final ReviewService reviewService;

    @Autowired
    public KafkaConsumer(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @StreamListener("deleteReviewConsumer-in-0")
    public void deleteReview(Message<Map<String, Object>> message) {
        try {
            Long locationId = ((Number) message.getPayload().get("locationId")).longValue();
            int deletedCount = reviewService.deleteReviewsByLocationId(locationId);
            log.info("인식된 위치 ID: {}", locationId);
            log.info("삭제된 리뷰 수: {}", deletedCount);
        } catch (Exception e) {
            log.error("다음 메시지가 포함된 리뷰 삭제 실패: {}", message, e);
        }
    }
}