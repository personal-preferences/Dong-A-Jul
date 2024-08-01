package org.personal.review_service.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.personal.review_service.domain.QReview;
import org.personal.review_service.domain.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ReviewRepository {

    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    public List<Review> findReviewsByUserId(Long userId) {
        QReview review = QReview.review;

        return jpaQueryFactory
                .selectFrom(review)
                .where(review.userId.eq(userId))
                .fetch();
    }

    public List<Review> findReviewsByLocationId(Long locationId) {
        QReview review = QReview.review;

        return jpaQueryFactory
                .selectFrom(review)
                .where(review.locationId.eq(locationId))
                .fetch();
    }
}
