package org.personal.review_service.repository;


import org.personal.review_service.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long>, ReviewRepositoryCustom{
    // JPA 쿼리 메서드와 커스텀 메서드를 모두 사용 가능

}
