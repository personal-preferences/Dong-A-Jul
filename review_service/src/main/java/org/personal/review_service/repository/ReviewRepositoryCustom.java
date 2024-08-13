package org.personal.review_service.repository;

import org.personal.review_service.domain.Review;
import org.personal.review_service.response.ReviewSummary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ReviewRepositoryCustom {

    Optional<Review> findByReviewId(Long reviewId);

    Page<Review> findReviewsByUserId(Long userId, Pageable pageable);

    Page<Review> findReviewsByLocationId(Long locationId, Pageable pageable);

    Optional<ReviewSummary> getReviewSummaryByLocationId(Long locationId);
}
