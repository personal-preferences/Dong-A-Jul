package org.personal.review_service.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "review", schema = "public")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id", nullable = false)
    private Long reviewId;

    @Column(name = "review_content", nullable = false)
    private String reviewContent;

    @Column(name = "review_score", nullable = false)
    private Integer reviewScore;

    @Column(name = "review_registered_date", nullable = false)
    private String reviewRegisteredDate;

    @Column(name = "review_is_deleted", nullable = false)
    private Boolean reviewIsDeleted;

    @JoinColumn(name = "user_id", nullable = false)
    private Long userId;

    @JoinColumn(name = "location_id", nullable = false)
    private Long locationId;

    @Builder
    public Review(Long reviewId, String reviewContent, Integer reviewScore, String reviewRegisteredDate,
                  Boolean reviewIsDeleted, Long userId, Long locationId) {
        this.reviewId = reviewId;
        this.reviewContent = reviewContent;
        this.reviewScore = reviewScore;
        this.reviewRegisteredDate = reviewRegisteredDate;
        this.reviewIsDeleted = reviewIsDeleted;
        this.userId = userId;
        this.locationId = locationId;
    }

    public void markAsDeleted(){
        this.reviewIsDeleted = Boolean.TRUE;
    }
}
