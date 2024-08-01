package org.personal.review_service.service;

import org.personal.review_service.common.DateParsing;
import org.personal.review_service.domain.Review;
import org.personal.review_service.exception.ReviewNotFoundException;
import org.personal.review_service.repository.ReviewRepository;
import org.personal.review_service.request.ReviewCreate;
import org.personal.review_service.response.ReviewResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
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

        return ReviewResponse.builder()
                .reviewId(review.getReviewId())
                .reviewContent(review.getReviewContent())
                .reviewScore(review.getReviewScore())
                .reviewRegisteredDate(review.getReviewRegisteredDate())
                .reviewIsDeleted(review.getReviewIsDeleted())
                .userId(review.getUserId())
                .locationId(review.getLocationId())
                .build();
    }

    @Override
    public ReviewResponse getReview(Long reviewId) {
        Review review = reviewRepository.findByReviewId(reviewId)
                .orElseThrow(() -> new ReviewNotFoundException("Review not found for id: " + reviewId));

        return ReviewResponse.builder()
                .reviewId(review.getReviewId())
                .reviewContent(review.getReviewContent())
                .reviewScore(review.getReviewScore())
                .reviewRegisteredDate(review.getReviewRegisteredDate())
                .reviewIsDeleted(review.getReviewIsDeleted())
                .userId(review.getUserId())
                .locationId(review.getLocationId())
                .build();
    }

    @Override
    public List<ReviewResponse> getReviewListByUserId(Long userId) {
        List<Review> reviewList = reviewRepository.findReviewsByUserId(userId);

        if (reviewList.isEmpty()) {
            throw new ReviewNotFoundException("No reviews found for user id: " + userId);
        }

        return reviewList.stream()
                .map(review -> ReviewResponse.builder()
                        .reviewId(review.getReviewId())
                        .reviewContent(review.getReviewContent())
                        .reviewScore(review.getReviewScore())
                        .reviewRegisteredDate(review.getReviewRegisteredDate())
                        .reviewIsDeleted(review.getReviewIsDeleted())
                        .userId(review.getUserId())
                        .locationId(review.getLocationId())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public List<ReviewResponse> getReviewListByLocationId(Long locationId) {
        List<Review> reviewList = reviewRepository.findReviewsByLocationId(locationId);

        if (reviewList.isEmpty()) {
            throw new ReviewNotFoundException("No reviews found for location id: " + locationId);
        }

        return reviewList.stream()
                .map(review -> ReviewResponse.builder()
                        .reviewId(review.getReviewId())
                        .reviewContent(review.getReviewContent())
                        .reviewScore(review.getReviewScore())
                        .reviewRegisteredDate(review.getReviewRegisteredDate())
                        .reviewIsDeleted(review.getReviewIsDeleted())
                        .userId(review.getUserId())
                        .locationId(review.getLocationId())
                        .build())
                .collect(Collectors.toList());
    }


    @Override
    public Boolean deleteReviewByReviewId(Long reviewId) {
        Review review = reviewRepository.findByReviewId(reviewId)
                .orElseThrow(() -> new ReviewNotFoundException("Review not found for id: " + reviewId));

        review.markAsDeleted();
        reviewRepository.save(review);
        return true;
    }
}
