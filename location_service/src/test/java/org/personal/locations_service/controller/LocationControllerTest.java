package org.personal.locations_service.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.personal.locations_service.repository.LocationRepository;
import org.personal.locations_service.request.LocationCreate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class LocationControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    LocationRepository locationRepository;

    @BeforeEach
    void clean() {
        locationRepository.deleteAll();
    }

    @Test
    @DisplayName("화장실 위치 등록 성공")
    void addSuccessful() throws Exception {
        // given
        LocationCreate request = LocationCreate
                .builder()
                .name("홍대 화장실")
                .roadAddress("서울 마포구 서교동 1길")
                .jibunAddress("서울 마포구 동교동 150-1")
                .latitude(10.23f)
                .longitude(32.99f)
                .build();

        String json = objectMapper.writeValueAsString(request);

        // expected
        mockMvc.perform(post("/locations")
                    .contentType(APPLICATION_JSON)
                    .content(json))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("화장실 위치 등록 시 name, roadAddress, jibunAddress값은 필수다.")
    void addToiletRequiredValueException() throws Exception {
        // given
        LocationCreate request = LocationCreate
                .builder()
//                .name("홍대 화장실")
//                .roadAddress("서울 마포구 서교동 1길")
//                .jibunAddress("서울 마포구 동교동 150-1")
                .latitude(10.23f)
                .longitude(32.99f)
                .build();

        String json = objectMapper.writeValueAsString(request);

        // expected
        mockMvc.perform(post("/locations")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
                .andExpect(jsonPath("$.validation.name").value("화장실 이름을 입력하세요."))
                .andExpect(jsonPath("$.validation.roadAddress").value("도로명 주소를 입력하세요."))
                .andExpect(jsonPath("$.validation.jibunAddress").value("지번 주소를 입력하세요."))
                .andDo(print());
    }

    @Test
    void get() {
    }
}