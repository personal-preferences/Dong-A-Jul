package org.personal.review_service.service;

import org.personal.review_service.common.DateParsing;
import org.personal.review_service.common.ReviewResponseBuilder;
import org.personal.review_service.domain.Review;
import org.personal.review_service.exception.ReviewNotFoundException;
import org.personal.review_service.repository.ReviewRepository;
import org.personal.review_service.request.ReviewCreate;
import org.personal.review_service.response.ReviewResponse;
import org.personal.review_service.response.ReviewSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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
    public Page<ReviewResponse> getReviewListByUserId(Long userId, String sort, String direction, int page) {
        Page<Review> reviewList = reviewRepository.findReviewsByUserId(userId, createPageable(sort, direction, page));

        if (reviewList.isEmpty()) {
            throw new ReviewNotFoundException("다음 회원 ID에 알맞는 리뷰를 찾지 못했습니다: " + userId);
        }

        return reviewList.map(reviewResponseBuilder::build);
    }

    @Override
    public Page<ReviewResponse> getReviewListByLocationId(Long locationId, String sort, String direction, int page) {
        Page<Review> reviewList = reviewRepository.findReviewsByLocationId(locationId, createPageable(sort, direction, page));

        if (reviewList.isEmpty()) {
            throw new ReviewNotFoundException("다음 위치 ID에 알맞는 리뷰를 찾지 못했습니다: " + locationId);
        }

        return reviewList.map(reviewResponseBuilder::build);
    }

    @Override
    public ReviewSummary getReviewSummaryByLocationId(Long locationId) {
        return reviewRepository.getReviewSummaryByLocationId(locationId)
                .orElseThrow(() -> new ReviewNotFoundException("다음 위치 ID에 알맞는 리뷰를 찾지 못했습니다: " + locationId));
    }

    @Override
    public ReviewSummary getReviewSummaryByUserId(Long userId) {
        return reviewRepository.getReviewSummaryByUserId(userId)
                .orElseThrow(() -> new ReviewNotFoundException("다음 회원 ID에 알맞는 리뷰를 찾지 못했습니다: " + userId));
    }

    @Override
    public Boolean deleteReviewByReviewId(Long reviewId) {
        Review review = reviewRepository.findByReviewId(reviewId)
                .orElseThrow(() -> new ReviewNotFoundException("다음 리뷰 ID에 알맞는 리뷰를 찾지 못했습니다: " + reviewId));

        review.markAsDeleted();
        reviewRepository.save(review);
        return true;
    }

    @Override
    public int deleteReviewsByLocationId(Long locationId) {
        List<Review> reviews = reviewRepository.findReviewsByLocationId(locationId, Pageable.unpaged()).getContent();
        if (reviews.isEmpty()) {
            throw new ReviewNotFoundException("다음 위치 ID에 알맞는 리뷰를 찾지 못했습니다: " + locationId);
        }

        reviews.forEach(Review::markAsDeleted);
        reviewRepository.saveAll(reviews);
        return reviews.size();
    }

    @Override
    public Pageable createPageable(String sort, String direction, int page) {
        Sort.Direction sortDirection = Sort.Direction.fromString(direction);
        return PageRequest.of(page, 10, Sort.by(sortDirection, sort));
    }
}
