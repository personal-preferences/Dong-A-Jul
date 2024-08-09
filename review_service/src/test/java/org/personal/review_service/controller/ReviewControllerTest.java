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
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.*;
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
        when(reviewService.getReview(anyLong())).thenThrow(new ReviewNotFoundException("해당 리뷰를 찾을 수 없습니다."));

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
        // 정렬 및 페이지 관련 파라미터 정의
        String sort = "reviewRegisteredDate";
        String direction = "ASC";
        String page = "0";

        // given
        ReviewResponse reviewResponse1 = new ReviewResponse(1L, "저에게 큰 도움이 되었습니다!", 5, "2024-01-03 14:00:00", false, 1L, 1L);
        ReviewResponse reviewResponse2 = new ReviewResponse(2L, "좋은 장소입니다!", 4, "2024-01-02 12:00:00", false, 1L, 2L);

        // 정렬된 리스트 (ASC 정렬 기준, direction에 맞춤)
        List<ReviewResponse> reviewResponses = Arrays.asList(reviewResponse2, reviewResponse1);
        Page<ReviewResponse> reviewResponsePage = new PageImpl<>(reviewResponses, PageRequest.of(Integer.parseInt(page), 10, Sort.by(Sort.Order.by(sort).with(Sort.Direction.fromString(direction)))), reviewResponses.size());

        when(reviewService.getReviewListByUserId(eq(1L), any(Pageable.class))).thenReturn(reviewResponsePage);

        // when
        ResultActions result = mockMvc.perform(get("/reviews/user/{userId}", 1L)
                .param("sort", sort)
                .param("direction", direction)
                .param("page", page)
                .contentType(MediaType.APPLICATION_JSON));

        // then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(2)))
                .andExpect(jsonPath("$.content[0].reviewId").value(reviewResponse2.reviewId()))  // ASC이므로 reviewResponse2가 첫번째로 나와야 함
                .andExpect(jsonPath("$.content[1].reviewId").value(reviewResponse1.reviewId()))  // reviewResponse1가 두번째
                .andDo(print());
    }

    @Test
    @DisplayName("리뷰 회원ID로 조회 - 예외 발생")
    void getReviewListByUserIdNotFound() throws Exception {
        // given
        when(reviewService.getReviewListByUserId(eq(1L), any(Pageable.class))).thenThrow(new ReviewNotFoundException("유저에 해당하는 리뷰를 찾을 수 없습니다."));

        // when
        ResultActions result = mockMvc.perform(get("/reviews/user/{userId}", 1L)
                .param("sort", "reviewRegisteredDate")
                .param("direction", "DESC")
                .param("page", "0")
                .contentType(MediaType.APPLICATION_JSON));

        // then
        result.andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    @DisplayName("리뷰 위치ID로 조회")
    void getReviewListByLocationId() throws Exception {
        // 정렬 및 페이지 관련 파라미터 정의
        String sort = "reviewScore";
        String direction = "DESC";
        String page = "0";

        // given
        ReviewResponse reviewResponse1 = new ReviewResponse(1L, "저에게 큰 도움이 되었습니다!", 4, "2024-01-01 12:00:00", false, 1L, 1L);
        ReviewResponse reviewResponse2 = new ReviewResponse(2L, "좋은 장소입니다!", 5, "2024-01-02 14:00:00", false, 2L, 1L);

        // 정렬된 리스트 (DESC 정렬 기준, reviewScore 기준)
        List<ReviewResponse> reviewResponses = Arrays.asList(reviewResponse2, reviewResponse1);
        Page<ReviewResponse> reviewResponsePage = new PageImpl<>(reviewResponses, PageRequest.of(Integer.parseInt(page), 10, Sort.by(Sort.Order.by(sort).with(Sort.Direction.fromString(direction)))), reviewResponses.size());

        when(reviewService.getReviewListByLocationId(eq(1L), any(Pageable.class))).thenReturn(reviewResponsePage);

        // when
        ResultActions result = mockMvc.perform(get("/reviews/location/{locationId}", 1L)
                .param("sort", sort)
                .param("direction", direction)
                .param("page", page)
                .contentType(MediaType.APPLICATION_JSON));

        // then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(2)))
                .andExpect(jsonPath("$.content[0].reviewId").value(reviewResponse2.reviewId()))  // DESC이므로 reviewResponse2가 첫번째로 나와야 함
                .andExpect(jsonPath("$.content[1].reviewId").value(reviewResponse1.reviewId()))  // reviewResponse1가 두번째
                .andDo(print());
    }

    @Test
    @DisplayName("리뷰 위치ID로 조회 - 예외 발생")
    void getReviewListByLocationIdNotFound() throws Exception {
        // given
        when(reviewService.getReviewListByLocationId(eq(1L), any(Pageable.class))).thenThrow(new ReviewNotFoundException("위치에 해당하는 리뷰를 찾을 수 없습니다."));

        // when
        ResultActions result = mockMvc.perform(get("/reviews/location/{locationId}", 1L)
                .param("sort", "reviewScore")
                .param("direction", "DESC")
                .param("page", "0")
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
        when(reviewService.deleteReviewByReviewId(anyLong())).thenThrow(new ReviewNotFoundException("해당 리뷰를 찾을 수 없습니다."));

        // when
        ResultActions result = mockMvc.perform(delete("/reviews/{reviewId}", 1L)
                .contentType(MediaType.APPLICATION_JSON));

        // then
        result.andExpect(status().isNotFound())
                .andDo(print());
    }
}
