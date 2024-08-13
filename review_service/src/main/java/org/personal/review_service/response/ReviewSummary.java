package org.personal.review_service.response;

import lombok.Builder;

public record ReviewSummary(
        Double averageScore,
        Long reviewCount,
        Long id
) {
    @Builder
    public ReviewSummary(Double averageScore, Long reviewCount, Long id) {
        this.averageScore = averageScore;
        this.reviewCount = reviewCount;
        this.id = id;
    }
}