package org.personal.review_service.response;

public record ResponseReviewDTO(
        Long reviewId,
        String reviewContent,
        Long reviewScore,
        String reviewRegisteredDate,
        Boolean reviewIsDeleted,
        Long userId,
        Long locationId
) {
}