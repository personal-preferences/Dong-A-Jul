package org.personal.review_service.controller;

import jakarta.validation.Valid;
import org.personal.review_service.exception.ReviewNotFoundException;
import org.personal.review_service.request.ReviewCreate;
import org.personal.review_service.response.ReviewResponse;
import org.personal.review_service.response.ReviewSummary;
import org.personal.review_service.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static org.personal.review_service.common.Constants.PAGE_SIZE;
import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.data.domain.Sort.Direction.DESC;

@RestController
@RequestMapping("/reviews")
@Validated
public class ReviewController {

    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping()
    public ResponseEntity<ReviewResponse> createReview(@RequestBody @Valid ReviewCreate reviewCreate) {
        ReviewResponse reviewResponse = reviewService.createReview(reviewCreate);
        return new ResponseEntity<>(reviewResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<ReviewResponse> getReview(@PathVariable Long reviewId) {
        try {
            ReviewResponse reviewResponse = reviewService.getReview(reviewId);
            return new ResponseEntity<>(reviewResponse, HttpStatus.OK);
        } catch (ReviewNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 리뷰가 없는 경우 204 반환
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<ReviewResponse>> getReviewListByUserId(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "reviewRegisteredDate") String sort,
            @RequestParam(defaultValue = "DESC") String direction,
            @RequestParam(defaultValue = "0") int page) {
        try {
            Sort.Direction sortDirection = Sort.Direction.fromString(direction);
            Pageable pageable = PageRequest.of(page, 10, Sort.by(sortDirection, sort));
            Page<ReviewResponse> reviewResponses = reviewService.getReviewListByUserId(userId, pageable);
            return new ResponseEntity<>(reviewResponses, HttpStatus.OK);
        } catch (ReviewNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }


    @GetMapping("/location/{locationId}")
    public ResponseEntity<Page<ReviewResponse>> getReviewListByLocationId(
            @PathVariable Long locationId,
            @RequestParam(defaultValue = "reviewRegisteredDate") String sort,
            @RequestParam(defaultValue = "DESC") String direction,
            @RequestParam(defaultValue = "0") int page) {
        try {
            Sort.Direction sortDirection = Sort.Direction.fromString(direction);
            Pageable pageable = PageRequest.of(page, 10, Sort.by(sortDirection, sort));
            Page<ReviewResponse> reviewResponses = reviewService.getReviewListByLocationId(locationId, pageable);
            return new ResponseEntity<>(reviewResponses, HttpStatus.OK);
        } catch (ReviewNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/location/{locationId}/summary")
    public ResponseEntity<ReviewSummary> getReviewSummaryByLocationId(
            @PathVariable Long locationId) {
        ReviewSummary reviewSummary = reviewService.getReviewSummaryByLocationId(locationId);
        return new ResponseEntity<>(reviewSummary, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}/summary")
    public ResponseEntity<ReviewSummary> getReviewSummaryByUserId(
            @PathVariable Long userId) {
        ReviewSummary reviewSummary = reviewService.getReviewSummaryByUserId(userId);
        return new ResponseEntity<>(reviewSummary, HttpStatus.OK);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long reviewId) {
        try {
            reviewService.deleteReviewByReviewId(reviewId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 삭제 성공 시 204 반환
        } catch (ReviewNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 리뷰가 없는 경우 404 반환
        }
    }

    @DeleteMapping("/location/{locationId}")
    public ResponseEntity<Integer> deleteReviewsByLocationId(@PathVariable Long locationId) {
        try {
            int deletedCount = reviewService.deleteReviewsByLocationId(locationId);
            return new ResponseEntity<>(deletedCount, HttpStatus.OK); // 삭제 성공 시 200 반환
        } catch (ReviewNotFoundException e) {
            return new ResponseEntity<>(0, HttpStatus.NOT_FOUND); // 리뷰가 없는 경우 404 반환
        }
    }
}
