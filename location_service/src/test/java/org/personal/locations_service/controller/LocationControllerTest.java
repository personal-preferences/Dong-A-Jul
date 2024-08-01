package org.personal.locations_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.personal.locations_service.domain.Location;
import org.personal.locations_service.repository.LocationRepository;
import org.personal.locations_service.request.LocationCreate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.stream.IntStream;

import static org.hamcrest.Matchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
    @DisplayName("화장실 위치 1개 조회")
    void getToiletLocation() throws Exception {
        // given
        Location location = Location
                .builder()
                .name("홍대 화장실")
                .roadAddress("서울 마포구 서교동 1길")
                .jibunAddress("서울 마포구 동교동 150-1")
                .latitude(10.23f)
                .longitude(32.99f)
                .build();
        locationRepository.save(location);

        // expected
        mockMvc.perform(get("/locations/{toiletName}", location.getName())
                    .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(location.getId()))
                .andExpect(jsonPath("$.name").value(location.getName()))
                .andExpect(jsonPath("$.roadAddress").value(location.getRoadAddress()))
                .andExpect(jsonPath("$.jibunAddress").value(location.getJibunAddress()))
                .andExpect(jsonPath("$.latitude").value(location.getLatitude()))
                .andExpect(jsonPath("$.longitude").value(location.getLongitude()))
                .andDo(print());
    }

    @Test
    @DisplayName("존재하지 않는 화장실 조회")
    void getNonExistentLocation() throws Exception {
        // given
        String nonExistToilet = "강남화장실";

        // expected
        mockMvc.perform(get("/locations/{toiletName}", nonExistToilet)
                    .contentType(APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    @DisplayName("화장실 여러개 조회")
    void getLocationList() throws Exception {
        // given
        List<Location> requestLocationList = IntStream.range(0, 30)
                .mapToObj(i -> Location.builder()
                        .name("홍대 화장실" + i)
                        .roadAddress("서울 마포구 서교동 1길" + i)
                        .jibunAddress("서울 마포구 동교동 150-1" + i)
                        .latitude(10.23f + i)
                        .longitude(32.99f + i)
                        .build())
                .toList();

        locationRepository.saveAll(requestLocationList);

        // expected
        mockMvc.perform(get("/locations?page=1&size=10")
                    .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(10)))
                .andExpect(jsonPath("$[0].name").value("홍대 화장실29"))
                .andExpect(jsonPath("$[0].roadAddress").value("서울 마포구 서교동 1길29"))
                .andExpect(jsonPath("$[0].jibunAddress").value("서울 마포구 동교동 150-129"))
                .andExpect(jsonPath("$[0].latitude").value("39.23"))
                .andExpect(jsonPath("$[0].longitude").value("61.99"))
                .andDo(print());
    }
}