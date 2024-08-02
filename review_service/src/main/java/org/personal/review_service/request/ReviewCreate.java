package org.personal.review_service.request;

import jakarta.validation.constraints.*;
import lombok.Builder;

public record ReviewCreate(
        @NotNull(message = "리뷰 내용은 null일 수 없습니다.")
        String reviewContent,
        @NotNull(message = "리뷰 점수는 null일 수 없습니다.")
        @Max(value = 5, message = "리뷰 점수는 1~5 사이의 값이어야 합니다.")
        @Min(value = 1, message = "리뷰 점수는 1~5 사이의 값이어야 합니다.")
        Integer reviewScore,
        @NotNull(message = "회원 ID는 null일 수 없습니다.")
        Long userId,
        @NotNull(message = "위치 ID는 null일 수 없습니다.")
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