package org.personal.review_service.repository;


public interface ReviewRepository extends ReviewJpaRepository, ReviewRepositoryCustom{
    // JPA 쿼리 메서드와 커스텀 메서드를 모두 사용 가능

}
