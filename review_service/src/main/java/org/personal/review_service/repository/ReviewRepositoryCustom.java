package org.personal.review_service.repository;

import org.personal.review_service.domain.Review;

import java.util.List;

public interface ReviewRepositoryCustom {

    List<Review> findReviewsByUserId(Long userId);

    List<Review> findReviewsByLocationId(Long locationId);
}
