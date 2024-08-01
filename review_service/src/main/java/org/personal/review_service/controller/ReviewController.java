package org.personal.review_service.controller;

import jakarta.validation.Valid;
import org.personal.review_service.exception.ReviewNotFoundException;
import org.personal.review_service.request.ReviewCreate;
import org.personal.review_service.response.ReviewResponse;
import org.personal.review_service.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/review")
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
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 리뷰가 없는 경우 404 반환
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ReviewResponse>> getReviewListByUserId(@PathVariable Long userId) {
        try {
            List<ReviewResponse> reviewResponses = reviewService.getReviewListByUserId(userId);
            return new ResponseEntity<>(reviewResponses, HttpStatus.OK);
        } catch (ReviewNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/location/{locationId}")
    public ResponseEntity<List<ReviewResponse>> getReviewListByLocationId(@PathVariable Long locationId) {
        try {
            List<ReviewResponse> reviewResponses = reviewService.getReviewListByLocationId(locationId);
            return new ResponseEntity<>(reviewResponses, HttpStatus.OK);
        } catch (ReviewNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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
