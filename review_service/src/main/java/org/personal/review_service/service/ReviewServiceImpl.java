package org.personal.review_service.service;

import org.personal.review_service.common.DateParsing;
import org.personal.review_service.common.ReviewResponseBuilder;
import org.personal.review_service.domain.Review;
import org.personal.review_service.exception.ReviewNotFoundException;
import org.personal.review_service.repository.ReviewRepository;
import org.personal.review_service.request.ReviewCreate;
import org.personal.review_service.response.ReviewResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewResponseBuilder reviewResponseBuilder;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
        this.reviewResponseBuilder = new ReviewResponseBuilder();
    }
    @Override
    public ReviewResponse createReview(ReviewCreate reviewCreate) {
        Review newReview = Review.builder()
                .reviewContent(reviewCreate.reviewContent())
                .reviewScore(reviewCreate.reviewScore())
                .userId(reviewCreate.userId())
                .locationId(reviewCreate.locationId())
                .reviewIsDeleted(Boolean.FALSE)
                .reviewRegisteredDate(DateParsing.LdtToStr(LocalDateTime.now()))
                .build();

        Review review = reviewRepository.save(newReview);
        return reviewResponseBuilder.build(review);
    }

    @Override
    public ReviewResponse getReview(Long reviewId) {
        Review review = reviewRepository.findByReviewId(reviewId)
                .orElseThrow(() -> new ReviewNotFoundException("다음 리뷰 ID에 알맞는 리뷰를 찾지 못했습니다: " + reviewId));

        return reviewResponseBuilder.build(review);
    }

    @Override
    public Page<ReviewResponse> getReviewListByUserId(Long userId, Pageable pageable) {
        Page<Review> reviewList = reviewRepository.findReviewsByUserId(userId, pageable);

        if (reviewList.isEmpty()) {
            throw new ReviewNotFoundException("다음 회원 ID에 알맞는 리뷰를 찾지 못했습니다: " + userId);
        }

        return reviewList.map(reviewResponseBuilder::build);
    }

    @Override
    public Page<ReviewResponse> getReviewListByLocationId(Long locationId, Pageable pageable) {
        Page<Review> reviewList = reviewRepository.findReviewsByLocationId(locationId, pageable);

        if (reviewList.isEmpty()) {
            throw new ReviewNotFoundException("다음 위치 ID에 알맞는 리뷰를 찾지 못했습니다: " + locationId);
        }

        return reviewList.map(reviewResponseBuilder::build);
    }


    @Override
    public Boolean deleteReviewByReviewId(Long reviewId) {
        Review review = reviewRepository.findByReviewId(reviewId)
                .orElseThrow(() -> new ReviewNotFoundException("다음 리뷰 ID에 알맞는 리뷰를 찾지 못했습니다: " + reviewId));

        review.markAsDeleted();
        reviewRepository.save(review);
        return true;
    }
}
