package org.personal.review_service.repository;


import org.personal.review_service.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long>, ReviewRepositoryCustom{
    List<Review> findByLocationIdAndReviewIsDeletedFalse(Long locationId);


}
