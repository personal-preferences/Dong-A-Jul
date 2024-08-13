package org.personal.review_service.service;

import org.personal.review_service.request.ReviewCreate;
import org.personal.review_service.response.ReviewResponse;
import org.personal.review_service.response.ReviewSummary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewService {

    ReviewResponse createReview(ReviewCreate reviewCreate);

    ReviewResponse getReview(Long reviewId);

    Page<ReviewResponse> getReviewListByUserId(Long userId, Pageable pageable);

    Page<ReviewResponse> getReviewListByLocationId(Long locationId, Pageable pageable);

    Boolean deleteReviewByReviewId(Long reviewId);

    ReviewSummary getReviewSummaryByLocationId(Long locationId);

    ReviewSummary getReviewSummaryByUserId(Long userId);
}
