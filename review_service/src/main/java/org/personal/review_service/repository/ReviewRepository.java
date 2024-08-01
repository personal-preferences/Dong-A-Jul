package org.personal.review_service.domain.repository;

import org.personal.review_service.domain.aggregate.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Integer> {


}
