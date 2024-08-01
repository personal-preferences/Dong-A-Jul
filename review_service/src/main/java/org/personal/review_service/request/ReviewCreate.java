package org.personal.review_service.request;

import jakarta.validation.constraints.*;
import lombok.Builder;

public record ReviewCreate(
        @NotNull(message = "Review content cannot be null")
        String reviewContent,
        @NotNull(message = "Review score cannot be null")
        @Max(value = 5, message = "Review score must be between 1 and 5")
        @Min(value = 1, message = "Review score must be between 1 and 5")
        Integer reviewScore,
        @NotNull(message = "User ID cannot be null")
        Long userId,
        @NotNull(message = "Location ID cannot be null")
        Long locationId
) {
    @Builder
    public ReviewCreate(String reviewContent, Integer reviewScore, Long userId, Long locationId) {
        this.reviewContent = reviewContent;
        this.reviewScore = reviewScore;
        this.userId = userId;
        this.locationId = locationId;
    }
}