package org.personal.review_service.request;

import lombok.Builder;

public record ReviewCreate(
        String reviewContent,
        Long reviewScore,
        Long userId,
        Long locationId
) {
    @Builder
    public ReviewCreate(String reviewContent, Long reviewScore, Long userId, Long locationId) {
        this.reviewContent = reviewContent;
        this.reviewScore = reviewScore;
        this.userId = userId;
        this.locationId = locationId;
    }
}