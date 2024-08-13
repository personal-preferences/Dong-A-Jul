package org.personal.review_service.repository;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.personal.review_service.domain.QReview;
import org.personal.review_service.domain.Review;
import org.personal.review_service.response.ReviewSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public Page<Review> findReviewsByUserId(Long userId, Pageable pageable) {
        QReview review = QReview.review;
        Sort.Order order = pageable.getSort().iterator().next();
        OrderSpecifier<?> orderSpecifier = new OrderSpecifier<>(
                order.isAscending() ? Order.ASC : Order.DESC,
                new PathBuilder(Review.class, "review").get(order.getProperty())
        );

        JPQLQuery<Review> query = jpaQueryFactory
                .selectFrom(review)
                .where(review.userId.eq(userId), review.reviewIsDeleted.eq(Boolean.FALSE))
                .orderBy(orderSpecifier)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        return new PageImpl<>(query.fetch(), pageable, query.fetchCount());
    }

    @Override
    public Page<Review> findReviewsByLocationId(Long locationId, Pageable pageable) {
        QReview review = QReview.review;
        Sort.Order order = pageable.getSort().iterator().next();
        OrderSpecifier<?> orderSpecifier = new OrderSpecifier<>(
                order.isAscending() ? Order.ASC : Order.DESC,
                new PathBuilder(Review.class, "review").get(order.getProperty())
        );

        JPQLQuery<Review> query = jpaQueryFactory
                .selectFrom(review)
                .where(review.locationId.eq(locationId), review.reviewIsDeleted.eq(Boolean.FALSE))
                .orderBy(orderSpecifier)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        return new PageImpl<>(query.fetch(), pageable, query.fetchCount());
    }

    @Override
    public Optional<ReviewSummary> getReviewSummaryByLocationId(Long locationId) {
        QReview review = QReview.review;

        JPQLQuery<Tuple> query = jpaQueryFactory
                .select(review.reviewScore.avg(), review.count())
                .from(review)
                .where(review.locationId.eq(locationId), review.reviewIsDeleted.eq(Boolean.FALSE));

        Tuple result = query.fetchOne();

        if (result != null) {
            Double averageScore = result.get(0, Double.class);
            long reviewCount = result.get(1, Long.class);

            averageScore = (averageScore != null) ? averageScore : 0.0;

            return Optional.of(new ReviewSummary(averageScore, reviewCount, locationId));
        } else {
            return Optional.empty();
        }
    }
}
