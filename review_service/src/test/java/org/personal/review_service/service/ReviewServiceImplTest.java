package org.personal.review_service.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.personal.review_service.domain.Review;
import org.personal.review_service.exception.ReviewNotFoundException;
import org.personal.review_service.repository.ReviewRepository;
import org.personal.review_service.request.ReviewCreate;
import org.personal.review_service.response.ReviewResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
class ReviewServiceImplTest {

    @Autowired
    private ReviewServiceImpl reviewService;

    @MockBean
    private ReviewRepository reviewRepository;

    @BeforeEach
    void setUp() {
        // Set up common mock behavior if needed
    }

    @Test
    @DisplayName("리뷰 등록")
    void createReview() {
        // given
        ReviewCreate reviewCreate = new ReviewCreate("좋은 리뷰입니다!", 5, 1L, 1L);
        Review review = Review.builder()
                .reviewId(1L)
                .reviewContent("좋은 리뷰입니다!")
                .reviewScore(5)
                .userId(1L)
                .locationId(1L)
                .reviewIsDeleted(Boolean.FALSE)
                .reviewRegisteredDate("2024-01-01 12:00:00")
                .build();
        when(reviewRepository.save(any(Review.class))).thenReturn(review);

        // when
        ReviewResponse reviewResponse = reviewService.createReview(reviewCreate);

        // then
        assertNotNull(reviewResponse);
        assertEquals(1L, reviewResponse.reviewId());
        assertEquals("좋은 리뷰입니다!", reviewResponse.reviewContent());
        verify(reviewRepository, times(1)).save(any(Review.class));
    }

    @Test
    @DisplayName("리뷰 단일 조회")
    void getReview() {
        // given
        Review review = Review.builder()
                .reviewId(1L)
                .reviewContent("좋은 리뷰입니다!")
                .reviewScore(5)
                .userId(1L)
                .locationId(1L)
                .reviewIsDeleted(Boolean.FALSE)
                .reviewRegisteredDate("2024-01-01 12:00:00")
                .build();
        when(reviewRepository.findByReviewId(anyLong())).thenReturn(Optional.of(review));

        // when
        ReviewResponse reviewResponse = reviewService.getReview(1L);

        // then
        assertNotNull(reviewResponse);
        assertEquals(1L, reviewResponse.reviewId());
        assertEquals("좋은 리뷰입니다!", reviewResponse.reviewContent());
        verify(reviewRepository, times(1)).findByReviewId(anyLong());
    }

    @Test
    @DisplayName("리뷰 단일 조회 - 예외 발생")
    void getReviewNotFound() {
        // given
        when(reviewRepository.findByReviewId(anyLong())).thenReturn(Optional.empty());

        // when
        ReviewNotFoundException exception = assertThrows(ReviewNotFoundException.class, () -> reviewService.getReview(1L));

        // then
        assertEquals("다음 리뷰 ID에 알맞는 리뷰를 찾지 못했습니다: 1", exception.getMessage());
        verify(reviewRepository, times(1)).findByReviewId(anyLong());
    }

    @Test
    @DisplayName("리뷰 회원ID로 조회")
    void getReviewListByUserId() {
        // given
        Review review1 = Review.builder()
                .reviewId(1L)
                .reviewContent("좋은 리뷰입니다!")
                .reviewScore(5)
                .userId(1L)
                .locationId(1L)
                .reviewIsDeleted(Boolean.FALSE)
                .reviewRegisteredDate("2024-01-01 12:00:00")
                .build();
        Review review2 = Review.builder()
                .reviewId(2L)
                .reviewContent("훌륭한 장소입니다!")
                .reviewScore(4)
                .userId(1L)
                .locationId(2L)
                .reviewIsDeleted(Boolean.FALSE)
                .reviewRegisteredDate("2024-01-02 14:00:00")
                .build();
        List<Review> reviews = Arrays.asList(review1, review2);
        when(reviewRepository.findReviewsByUserId(anyLong())).thenReturn(reviews);

        // when
        List<ReviewResponse> reviewResponses = reviewService.getReviewListByUserId(1L);

        // then
        assertNotNull(reviewResponses);
        assertEquals(2, reviewResponses.size());
        assertEquals(1L, reviewResponses.get(0).reviewId());
        assertEquals(2L, reviewResponses.get(1).reviewId());
        verify(reviewRepository, times(1)).findReviewsByUserId(anyLong());
    }

    @Test
    @DisplayName("리뷰 회원ID로 조회 - 예외 발생")
    void getReviewListByUserIdNotFound() {
        // given
        when(reviewRepository.findReviewsByUserId(anyLong())).thenReturn(List.of());

        // when
        ReviewNotFoundException exception = assertThrows(ReviewNotFoundException.class, () -> reviewService.getReviewListByUserId(1L));

        // then
        assertEquals("다음 회원 ID에 알맞는 리뷰를 찾지 못했습니다: 1", exception.getMessage());
        verify(reviewRepository, times(1)).findReviewsByUserId(anyLong());
    }

    @Test
    @DisplayName("리뷰 위치ID로 조회")
    void getReviewListByLocationId() {
        // given
        Review review1 = Review.builder()
                .reviewId(1L)
                .reviewContent("좋은 리뷰입니다!")
                .reviewScore(5)
                .userId(1L)
                .locationId(1L)
                .reviewIsDeleted(Boolean.FALSE)
                .reviewRegisteredDate("2024-01-01 12:00:00")
                .build();
        Review review2 = Review.builder()
                .reviewId(2L)
                .reviewContent("훌륭한 장소입니다!")
                .reviewScore(4)
                .userId(2L)
                .locationId(1L)
                .reviewIsDeleted(Boolean.FALSE)
                .reviewRegisteredDate("2024-01-02 14:00:00")
                .build();
        List<Review> reviews = Arrays.asList(review1, review2);
        when(reviewRepository.findReviewsByLocationId(anyLong())).thenReturn(reviews);

        // when
        List<ReviewResponse> reviewResponses = reviewService.getReviewListByLocationId(1L);

        // then
        assertNotNull(reviewResponses);
        assertEquals(2, reviewResponses.size());
        assertEquals(1L, reviewResponses.get(0).reviewId());
        assertEquals(2L, reviewResponses.get(1).reviewId());
        verify(reviewRepository, times(1)).findReviewsByLocationId(anyLong());
    }

    @Test
    @DisplayName("리뷰 위치ID로 조회 - 예외 발생")
    void getReviewListByLocationIdNotFound() {
        // given
        when(reviewRepository.findReviewsByLocationId(anyLong())).thenReturn(List.of());

        // when
        ReviewNotFoundException exception = assertThrows(ReviewNotFoundException.class, () -> reviewService.getReviewListByLocationId(1L));

        // then
        assertEquals("다음 위치 ID에 알맞는 리뷰를 찾지 못했습니다: 1", exception.getMessage());
        verify(reviewRepository, times(1)).findReviewsByLocationId(anyLong());
    }

    @Test
    @DisplayName("리뷰 삭제")
    void deleteReviewByReviewId() {
        // given
        Review review = Review.builder()
                .reviewId(1L)
                .reviewContent("좋은 리뷰입니다!")
                .reviewScore(5)
                .userId(1L)
                .locationId(1L)
                .reviewIsDeleted(Boolean.FALSE)
                .reviewRegisteredDate("2024-01-01 12:00:00")
                .build();
        when(reviewRepository.findByReviewId(anyLong())).thenReturn(Optional.of(review));

        // when
        Boolean result = reviewService.deleteReviewByReviewId(1L);

        // then
        assertTrue(result);
        verify(reviewRepository, times(1)).findByReviewId(anyLong());
        verify(reviewRepository, times(1)).save(any(Review.class));
    }

    @Test
    @DisplayName("리뷰 삭제 - 예외 발생")
    void deleteReviewByReviewIdNotFound() {
        // given
        when(reviewRepository.findByReviewId(anyLong())).thenReturn(Optional.empty());

        // when
        ReviewNotFoundException exception = assertThrows(ReviewNotFoundException.class, () -> reviewService.deleteReviewByReviewId(1L));

        // then
        assertEquals("다음 리뷰 ID에 알맞는 리뷰를 찾지 못했습니다: 1", exception.getMessage());
        verify(reviewRepository, times(1)).findByReviewId(anyLong());
    }
}
