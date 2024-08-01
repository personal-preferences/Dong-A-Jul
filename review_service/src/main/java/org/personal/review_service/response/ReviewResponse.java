package org.personal.review_service.response;

import lombok.Builder;

public record ReviewResponse(
        Long reviewId,
        String reviewContent,
        Long reviewScore,
        String reviewRegisteredDate,
        Boolean reviewIsDeleted,
        Long userId,
        Long locationId
) {
    @Builder
    public ReviewResponse(Long reviewId, String reviewContent, Long reviewScore,
                          String reviewRegisteredDate, Boolean reviewIsDeleted, Long userId, Long locationId) {
        this.reviewId = reviewId;
        this.reviewContent = reviewContent;
        this.reviewScore = reviewScore;
        this.reviewRegisteredDate = reviewRegisteredDate;
        this.reviewIsDeleted = reviewIsDeleted;
        this.userId = userId;
        this.locationId = locationId;
    }
}