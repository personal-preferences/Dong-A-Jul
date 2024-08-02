package org.personal.review_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.personal.review_service.exception.ReviewNotFoundException;
import org.personal.review_service.request.ReviewCreate;
import org.personal.review_service.response.ReviewResponse;
import org.personal.review_service.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReviewService reviewService;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    @DisplayName("리뷰 등록")
    void createReview() throws Exception {
        // given
        ReviewCreate reviewCreate = new ReviewCreate("저에게 큰 도움이 되었습니다!", 5, 1L, 1L);
        ReviewResponse reviewResponse = new ReviewResponse(1L, "저에게 큰 도움이 되었습니다!", 5, "2024-01-01 12:00:00", false, 1L, 1L);
        when(reviewService.createReview(any(ReviewCreate.class))).thenReturn(reviewResponse);

        // when
        ResultActions result = mockMvc.perform(post("/reviews")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reviewCreate)));

        // then
        result.andExpect(status().isCreated())
                .andExpect(jsonPath("$.reviewId").value(reviewResponse.reviewId()))
                .andExpect(jsonPath("$.reviewContent").value(reviewResponse.reviewContent()))
                .andDo(print());
    }

    @Test
    @DisplayName("리뷰 단일 조회")
    void getReview() throws Exception {
        // given
        ReviewResponse reviewResponse = new ReviewResponse(1L, "저에게 큰 도움이 되었습니다!", 5, "2024-01-01 12:00:00", false, 1L, 1L);
        when(reviewService.getReview(anyLong())).thenReturn(reviewResponse);

        // when
        ResultActions result = mockMvc.perform(get("/reviews/{reviewId}", 1L)
                .contentType(MediaType.APPLICATION_JSON));

        // then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.reviewId").value(reviewResponse.reviewId()))
                .andExpect(jsonPath("$.reviewContent").value(reviewResponse.reviewContent()))
                .andDo(print());
    }

    @Test
    @DisplayName("리뷰 단일 조회 - 예외 발생")
    void getReviewNotFound() throws Exception {
        // given
        when(reviewService.getReview(anyLong())).thenThrow(new ReviewNotFoundException("Review not found"));

        // when
        ResultActions result = mockMvc.perform(get("/reviews/{reviewId}", 1L)
                .contentType(MediaType.APPLICATION_JSON));

        // then
        result.andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    @DisplayName("리뷰 회원ID로 조회")
    void getReviewListByUserId() throws Exception {
        // given
        ReviewResponse reviewResponse1 = new ReviewResponse(1L, "저에게 큰 도움이 되었습니다!", 5, "2024-01-01 12:00:00", false, 1L, 1L);
        ReviewResponse reviewResponse2 = new ReviewResponse(2L, "좋은 장소입니다!", 4, "2024-01-02 14:00:00", false, 1L, 2L);
        List<ReviewResponse> reviewResponses = Arrays.asList(reviewResponse1, reviewResponse2);
        when(reviewService.getReviewListByUserId(anyLong())).thenReturn(reviewResponses);

        // when
        ResultActions result = mockMvc.perform(get("/reviews/user/{userId}", 1L)
                .contentType(MediaType.APPLICATION_JSON));

        // then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2))) // 리스트 크기 확인
                .andExpect(jsonPath("$[0].reviewId").value(reviewResponse1.reviewId()))
                .andExpect(jsonPath("$[1].reviewId").value(reviewResponse2.reviewId()))
                .andDo(print());
    }

    @Test
    @DisplayName("리뷰 회원ID로 조회 - 예외 발생")
    void getReviewListByUserIdNotFound() throws Exception {
        // given
        when(reviewService.getReviewListByUserId(anyLong())).thenThrow(new ReviewNotFoundException("No reviews found for user"));

        // when
        ResultActions result = mockMvc.perform(get("/reviews/user/{userId}", 1L)
                .contentType(MediaType.APPLICATION_JSON));

        // then
        result.andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    @DisplayName("리뷰 위치ID로 조회")
    void getReviewListByLocationId() throws Exception {
        // given
        ReviewResponse reviewResponse1 = new ReviewResponse(1L, "저에게 큰 도움이 되었습니다!", 5, "2024-01-01 12:00:00", false, 1L, 1L);
        ReviewResponse reviewResponse2 = new ReviewResponse(2L, "좋은 장소입니다!", 4, "2024-01-02 14:00:00", false, 2L, 1L);
        List<ReviewResponse> reviewResponses = Arrays.asList(reviewResponse1, reviewResponse2);
        when(reviewService.getReviewListByLocationId(anyLong())).thenReturn(reviewResponses);

        // when
        ResultActions result = mockMvc.perform(get("/reviews/location/{locationId}", 1L)
                .contentType(MediaType.APPLICATION_JSON));

        // then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2))) // 리스트 크기 확인
                .andExpect(jsonPath("$[0].reviewId").value(reviewResponse1.reviewId()))
                .andExpect(jsonPath("$[1].reviewId").value(reviewResponse2.reviewId()))
                .andDo(print());
    }

    @Test
    @DisplayName("리뷰 위치ID로 조회 - 예외 발생")
    void getReviewListByLocationIdNotFound() throws Exception {
        // given
        when(reviewService.getReviewListByLocationId(anyLong())).thenThrow(new ReviewNotFoundException("No reviews found for location"));

        // when
        ResultActions result = mockMvc.perform(get("/reviews/location/{locationId}", 1L)
                .contentType(MediaType.APPLICATION_JSON));

        // then
        result.andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    @DisplayName("리뷰 삭제")
    void deleteReview() throws Exception {
        // given
        when(reviewService.deleteReviewByReviewId(anyLong())).thenReturn(true);

        // when
        ResultActions result = mockMvc.perform(delete("/reviews/{reviewId}", 1L)
                .contentType(MediaType.APPLICATION_JSON));

        // then
        result.andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    @DisplayName("리뷰 삭제 - 예외 발생")
    void deleteReviewNotFound() throws Exception {
        // given
        when(reviewService.deleteReviewByReviewId(anyLong())).thenThrow(new ReviewNotFoundException("Review not found"));

        // when
        ResultActions result = mockMvc.perform(delete("/reviews/{reviewId}", 1L)
                .contentType(MediaType.APPLICATION_JSON));

        // then
        result.andExpect(status().isNotFound())
                .andDo(print());
    }
}
