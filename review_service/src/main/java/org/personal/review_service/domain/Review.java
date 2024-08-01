package org.personal.review_service.aggregate;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "review")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id", nullable = false)
    private Integer reviewId;

    @Column(name = "review_content", nullable = false)
    private String reviewContent;

    @Column(name = "review_score", nullable = false)
    private Integer reviewScore;

    @Column(name = "review_registred_date", nullable = false)
    private String reviewRegisteredDate;

    @Column(name = "review_is_deleted", nullable = false)
    private Boolean reviewIsDeleted;

    @JoinColumn(name = "user_id", nullable = false)
    private Integer userId;

    @JoinColumn(name = "location_id", nullable = false)
    private Integer locationId;

}
