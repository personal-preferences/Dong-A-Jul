package org.personal.review_service.repository;

import org.personal.review_service.domain.Review;

import java.util.List;
import java.util.Optional;

public interface ReviewRepositoryCustom {

    Optional<Review> findByReviewId(Long reviewId);

    List<Review> findReviewsByUserId(Long userId);

    List<Review> findReviewsByLocationId(Long locationId);
}
