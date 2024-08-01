package org.personal.review_service.domain.dto;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class ReviewDTO {

    private Integer reviewId;
    private String reviewContent;
    private Integer reviewScore;
    private String reviewRegisteredDate;
    private Boolean reviewIsDeleted;
    private Integer userId;
    private Integer locationId;

    @Builder
    public ReviewDTO(Integer reviewId, String reviewContent, Integer reviewScore, String reviewRegisteredDate,
                     Boolean reviewIsDeleted, Integer userId, Integer locationId) {
        this.reviewId = reviewId;
        this.reviewContent = reviewContent;
        this.reviewScore = reviewScore;
        this.reviewRegisteredDate = reviewRegisteredDate;
        this.reviewIsDeleted = reviewIsDeleted;
        this.userId = userId;
        this.locationId = locationId;
    }
}
