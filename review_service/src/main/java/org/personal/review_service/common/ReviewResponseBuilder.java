package org.personal.review_service.common;

import org.personal.review_service.domain.Review;
import org.personal.review_service.response.ReviewResponse;

public class ReviewResponseBuilder {
    public ReviewResponse build(Review review) {
        return ReviewResponse.builder()
                .reviewId(review.getReviewId())
                .reviewContent(review.getReviewContent())
                .reviewScore(review.getReviewScore())
                .reviewRegisteredDate(review.getReviewRegisteredDate())
                .reviewIsDeleted(review.getReviewIsDeleted())
                .userId(review.getUserId())
                .locationId(review.getLocationId())
                .build();
    }
}