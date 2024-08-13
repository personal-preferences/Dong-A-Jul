package org.personal.review_service.service;

import org.personal.review_service.request.ReviewCreate;
import org.personal.review_service.response.ReviewResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReviewService {

    ReviewResponse createReview(ReviewCreate reviewCreate);

    ReviewResponse getReview(Long reviewId);

    Page<ReviewResponse> getReviewListByUserId(Long userId, Pageable pageable);

    Page<ReviewResponse> getReviewListByLocationId(Long locationId, Pageable pageable);

    Boolean deleteReviewByReviewId(Long reviewId);
}
