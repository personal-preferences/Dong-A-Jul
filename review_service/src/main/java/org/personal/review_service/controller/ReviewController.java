package org.personal.review_service.controller;

import jakarta.validation.Valid;
import org.personal.review_service.exception.ReviewNotFoundException;
import org.personal.review_service.request.ReviewCreate;
import org.personal.review_service.response.ReviewResponse;
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

import java.util.List;

import static org.personal.review_service.common.Constants.PAGE_SIZE;

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
            @PageableDefault(size = PAGE_SIZE, sort = "reviewRegisteredDate", direction = Sort.Direction.DESC) Pageable pageable) {
        try {
            Page<ReviewResponse> reviewResponses = reviewService.getReviewListByUserId(userId, pageable);
            return new ResponseEntity<>(reviewResponses, HttpStatus.OK);
        } catch (ReviewNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }


    @GetMapping("/location/{locationId}")
    public ResponseEntity<Page<ReviewResponse>> getReviewListByLocationId(
            @PathVariable Long locationId,
            @PageableDefault(size = PAGE_SIZE, sort = "reviewRegisteredDate", direction = Sort.Direction.DESC) Pageable pageable) {
        try {
            Page<ReviewResponse> reviewResponses = reviewService.getReviewListByLocationId(locationId, pageable);
            return new ResponseEntity<>(reviewResponses, HttpStatus.OK);
        } catch (ReviewNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
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
}
