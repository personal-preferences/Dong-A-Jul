package org.personal.review_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.personal.review_service.exception.ReviewNotFoundException;
import org.personal.review_service.request.ReviewCreate;
import org.personal.review_service.response.ReviewResponse;
import org.personal.review_service.response.ReviewSummary;
import org.personal.review_service.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.personal.review_service.common.Constants.PAGE_SIZE;
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

    private ResultActions performRequest(String urlTemplate, Object... uriVars) throws Exception {
        return mockMvc.perform(get(urlTemplate, uriVars)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

    private ResultActions performPostRequest(String urlTemplate, Object body) throws Exception {
        return mockMvc.perform(post(urlTemplate)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andDo(print());
    }

    private ResultActions performDeleteRequest(String urlTemplate, Object... uriVars) throws Exception {
        return mockMvc.perform(delete(urlTemplate, uriVars)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

    @Test
    @DisplayName("리뷰 등록")
    void createReview() throws Exception {
        // given
        ReviewCreate reviewCreate = new ReviewCreate("저에게 큰 도움이 되었습니다!", 5, 1L, 1L);
        ReviewResponse reviewResponse = new ReviewResponse(1L, "저에게 큰 도움이 되었습니다!", 5, "2024-01-01 12:00:00", false, 1L, 1L);
        when(reviewService.createReview(any(ReviewCreate.class))).thenReturn(reviewResponse);

        // when
        ResultActions result = performPostRequest("/reviews", reviewCreate);

        // then
        result.andExpect(status().isCreated())
                .andExpect(jsonPath("$.reviewId").value(reviewResponse.reviewId()))
                .andExpect(jsonPath("$.reviewContent").value(reviewResponse.reviewContent()));
    }

    @Test
    @DisplayName("리뷰 단일 조회")
    void getReview() throws Exception {
        // given
        ReviewResponse reviewResponse = new ReviewResponse(1L, "저에게 큰 도움이 되었습니다!", 5, "2024-01-01 12:00:00", false, 1L, 1L);
        when(reviewService.getReview(anyLong())).thenReturn(reviewResponse);

        // when
        ResultActions result = performRequest("/reviews/{reviewId}", 1L);

        // then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.reviewId").value(reviewResponse.reviewId()))
                .andExpect(jsonPath("$.reviewContent").value(reviewResponse.reviewContent()));
    }

    @Test
    @DisplayName("리뷰 단일 조회 - 예외 발생")
    void getReviewNotFound() throws Exception {
        // given
        when(reviewService.getReview(anyLong())).thenThrow(new ReviewNotFoundException("해당 리뷰를 찾을 수 없습니다."));

        // when
        ResultActions result = performRequest("/reviews/{reviewId}", 1L);

        // then
        result.andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("리뷰 회원ID로 조회")
    void getReviewListByUserId() throws Exception {
        String sort = "reviewRegisteredDate";
        String direction = "ASC";
        String page = "0";

        // given
        ReviewResponse reviewResponse1 = new ReviewResponse(1L, "저에게 큰 도움이 되었습니다!", 5, "2024-01-03 14:00:00", false, 1L, 1L);
        ReviewResponse reviewResponse2 = new ReviewResponse(2L, "좋은 장소입니다!", 4, "2024-01-02 12:00:00", false, 1L, 2L);

        List<ReviewResponse> reviewResponses = Arrays.asList(reviewResponse2, reviewResponse1);
        Page<ReviewResponse> reviewResponsePage = new PageImpl<>(reviewResponses,
                PageRequest.of(Integer.parseInt(page), PAGE_SIZE,
                        Sort.by(Sort.Order.by(sort).with(Sort.Direction.fromString(direction)))),
                reviewResponses.size());

        when(reviewService.getReviewListByUserId(eq(1L), eq(sort), eq(direction), eq(Integer.parseInt(page))))
                .thenReturn(reviewResponsePage);

        // when
        ResultActions result = mockMvc.perform(get("/reviews/user/{userId}", 1L)
                        .param("sort", sort)
                        .param("direction", direction)
                        .param("page", page)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());

        // then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(2)))
                .andExpect(jsonPath("$.content[0].reviewId").value(reviewResponse2.reviewId()))
                .andExpect(jsonPath("$.content[1].reviewId").value(reviewResponse1.reviewId()));
    }

    @Test
    @DisplayName("리뷰 회원ID로 조회 - 예외 발생")
    void getReviewListByUserIdNotFound() throws Exception {
        String sort = "reviewRegisteredDate";
        String direction = "ASC";
        String page = "0";

        // given
        when(reviewService.getReviewListByUserId(eq(1L), eq(sort), eq(direction), eq(Integer.parseInt(page))))
                .thenThrow(new ReviewNotFoundException("유저에 해당하는 리뷰를 찾을 수 없습니다."));

        // when
        ResultActions result = mockMvc.perform(get("/reviews/user/{userId}", 1L)
                        .param("sort", sort)
                        .param("direction", direction)
                        .param("page", page)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());

        // then
        result.andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("리뷰 위치ID로 조회")
    void getReviewListByLocationId() throws Exception {
        String sort = "reviewScore";
        String direction = "DESC";
        String page = "0";

        // given
        ReviewResponse reviewResponse1 = new ReviewResponse(1L, "저에게 큰 도움이 되었습니다!", 4, "2024-01-01 12:00:00", false, 1L, 1L);
        ReviewResponse reviewResponse2 = new ReviewResponse(2L, "좋은 장소입니다!", 5, "2024-01-02 14:00:00", false, 2L, 1L);

        List<ReviewResponse> reviewResponses = Arrays.asList(reviewResponse2, reviewResponse1);
        Page<ReviewResponse> reviewResponsePage = new PageImpl<>(reviewResponses,
                PageRequest.of(Integer.parseInt(page), PAGE_SIZE,
                        Sort.by(Sort.Order.by(sort).with(Sort.Direction.fromString(direction)))),
                reviewResponses.size());

        when(reviewService.getReviewListByLocationId(eq(1L), eq(sort), eq(direction), eq(Integer.parseInt(page))))
                .thenReturn(reviewResponsePage);

        // when
        ResultActions result = mockMvc.perform(get("/reviews/location/{locationId}", 1L)
                        .param("sort", sort)
                        .param("direction", direction)
                        .param("page", page)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());

        // then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(2)))
                .andExpect(jsonPath("$.content[0].reviewId").value(reviewResponse2.reviewId()))
                .andExpect(jsonPath("$.content[1].reviewId").value(reviewResponse1.reviewId()));
    }

    @Test
    @DisplayName("리뷰 위치ID로 조회 - 예외 발생")
    void getReviewListByLocationIdNotFound() throws Exception {
        String sort = "reviewScore";
        String direction = "DESC";
        String page = "1";

        // given
        when(reviewService.getReviewListByLocationId(eq(1L), eq(sort), eq(direction), eq(Integer.parseInt(page))))
                .thenThrow(new ReviewNotFoundException("위치에 해당하는 리뷰를 찾을 수 없습니다."));

        // when
        ResultActions result = mockMvc.perform(get("/reviews/location/{locationId}", 1L)
                        .param("sort", sort)
                        .param("direction", direction)
                        .param("page", page)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());

        // then
        result.andExpect(status().isNoContent())
                .andDo(print());
    }


    @Test
    @DisplayName("리뷰 요약 조회(위치)")
    void getReviewSummaryByLocationId() throws Exception {
        // given
        Long locationId = 1L;
        ReviewSummary reviewSummary = new ReviewSummary(4.5, 10L, locationId);

        when(reviewService.getReviewSummaryByLocationId(locationId)).thenReturn(reviewSummary);

        // when
        ResultActions result = performRequest("/reviews/location/{locationId}/summary", locationId);

        // then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.averageScore").value(reviewSummary.averageScore()))
                .andExpect(jsonPath("$.reviewCount").value(reviewSummary.reviewCount()))
                .andExpect(jsonPath("$.id").value(reviewSummary.id()));
    }

    @Test
    @DisplayName("리뷰 요약 조회(위치) - 데이터 없음")
    void getReviewSummaryByLocationIdEmpty() throws Exception {
        // given
        Long locationId = 1L;
        ReviewSummary emptyReviewSummary = new ReviewSummary(0.0, 0L, locationId);

        when(reviewService.getReviewSummaryByLocationId(locationId)).thenReturn(emptyReviewSummary);

        // when
        ResultActions result = performRequest("/reviews/location/{locationId}/summary", locationId);

        // then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.averageScore").value(0.0))
                .andExpect(jsonPath("$.reviewCount").value(0))
                .andExpect(jsonPath("$.id").value(locationId));
    }

    @Test
    @DisplayName("리뷰 요약 조회(회원)")
    void getReviewSummaryByUserId() throws Exception {
        // given
        Long userId = 1L;
        ReviewSummary reviewSummary = new ReviewSummary(4.3, 10L, userId);

        when(reviewService.getReviewSummaryByUserId(userId)).thenReturn(reviewSummary);

        // when
        ResultActions result = performRequest("/reviews/user/{userId}/summary", userId);

        // then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.averageScore").value(reviewSummary.averageScore()))
                .andExpect(jsonPath("$.reviewCount").value(reviewSummary.reviewCount()))
                .andExpect(jsonPath("$.id").value(reviewSummary.id()));
    }

    @Test
    @DisplayName("리뷰 요약 조회(회원) - 데이터 없음")
    void getReviewSummaryByUserIdEmpty() throws Exception {
        // given
        Long userId = 1L;
        ReviewSummary emptyReviewSummary = new ReviewSummary(0.0, 0L, userId);

        when(reviewService.getReviewSummaryByUserId(userId)).thenReturn(emptyReviewSummary);

        // when
        ResultActions result = performRequest("/reviews/user/{userId}/summary", userId);

        // then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.averageScore").value(0.0))
                .andExpect(jsonPath("$.reviewCount").value(0))
                .andExpect(jsonPath("$.id").value(userId));
    }

    @Test
    @DisplayName("리뷰 삭제")
    void deleteReview() throws Exception {
        // given
        when(reviewService.deleteReviewByReviewId(anyLong())).thenReturn(true);

        // when
        ResultActions result = performDeleteRequest("/reviews/{reviewId}", 1L);

        // then
        result.andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("리뷰 삭제 - 예외 발생")
    void deleteReviewNotFound() throws Exception {
        // given
        when(reviewService.deleteReviewByReviewId(anyLong())).thenThrow(new ReviewNotFoundException("해당 리뷰를 찾을 수 없습니다."));

        // when
        ResultActions result = performDeleteRequest("/reviews/{reviewId}", 1L);

        // then
        result.andExpect(status().isNotFound());
    }
}
