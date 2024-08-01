package org.personal.review_service.request;

public record ReviewCreate(
        Long reviewId,
        String reviewContent,
        Long reviewScore,
        String reviewRegisteredDate,
        Boolean reviewIsDeleted,
        Long userId,
        Long locationId
) {
}