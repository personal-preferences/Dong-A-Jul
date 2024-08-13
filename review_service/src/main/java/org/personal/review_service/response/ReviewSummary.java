package org.personal.review_service.response;

import lombok.Builder;

public record ReviewSummary(
        Double averageScore,
        Long reviewCount,
        Long locationId
) {
    @Builder
    public ReviewSummary(Double averageScore, Long reviewCount, Long locationId) {
        this.averageScore = averageScore;
        this.reviewCount = reviewCount;
        this.locationId = locationId;
    }
}