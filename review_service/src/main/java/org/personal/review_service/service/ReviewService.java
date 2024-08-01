package org.personal.review_service.service;

import org.personal.review_service.request.ReviewCreate;
import org.personal.review_service.response.ReviewResponse;

import java.util.List;

public interface ReviewService {

    ReviewResponse createReview(ReviewCreate reviewCreate);
    ReviewResponse getReview(Long reviewId);
    List<ReviewResponse> getReviewListByUserId(Long userId);
    List<ReviewResponse> getReviewListByLocationId(Long locationId);
    Boolean deleteReviewByReviewId(Long reviewId);
}
