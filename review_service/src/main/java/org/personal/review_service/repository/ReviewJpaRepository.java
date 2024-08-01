package org.personal.review_service.repository;

import org.personal.review_service.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewJpaRepository extends JpaRepository<Review, Long> {
    // JPA 쿼리 메서드 정의
}
