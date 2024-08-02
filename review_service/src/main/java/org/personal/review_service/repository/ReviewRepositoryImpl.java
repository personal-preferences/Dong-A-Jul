package org.personal.review_service.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.personal.review_service.domain.QReview;
import org.personal.review_service.domain.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ReviewRepositoryImpl implements ReviewRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Autowired
    public ReviewRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public Optional<Review> findByReviewId(Long reviewId) {
        QReview review = QReview.review;

        Review foundReview = jpaQueryFactory
                .selectFrom(review)
                .where(review.reviewId.eq(reviewId), review.reviewIsDeleted.eq(Boolean.FALSE))
                .fetchOne();

        return Optional.ofNullable(foundReview);
    }

    @Override
    public List<Review> findReviewsByUserId(Long userId) {
        QReview review = QReview.review;

        return jpaQueryFactory
                .selectFrom(review)
                .where(review.userId.eq(userId), review.reviewIsDeleted.eq(Boolean.FALSE))
                .fetch();
    }

    @Override
    public List<Review> findReviewsByLocationId(Long locationId) {
        QReview review = QReview.review;

        return jpaQueryFactory
                .selectFrom(review)
                .where(review.locationId.eq(locationId), review.reviewIsDeleted.eq(Boolean.FALSE))
                .fetch();
    }
}
