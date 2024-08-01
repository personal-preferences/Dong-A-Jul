package org.personal.locations_service.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.personal.locations_service.domain.Location;
import org.personal.locations_service.repository.LocationRepository;
import org.personal.locations_service.request.LocationCreate;
import org.personal.locations_service.response.LocationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LocationServiceTest {

    @Autowired
    private LocationService locationService;

    @Autowired
    private LocationRepository locationRepository;

    @BeforeEach
    void clean() {
        locationRepository.deleteAll();
    }

    @Test
    @DisplayName("화장실 등록")
    void addToiletLocation(){
        // given
        LocationCreate locationCreate = LocationCreate
                .builder()
                .name("홍대 화장실")
                .roadAddress("서울 마포구 서교동 1길")
                .jibunAddress("서울 마포구 동교동 150-1")
                .latitude(10.23f)
                .longitude(32.99f)
                .build();

        // when
        locationService.add(locationCreate);

        // then
        assertEquals(1L, locationRepository.count());
        Location location = locationRepository.findAll().get(0);
        assertEquals("홍대 화장실", location.getName());
        assertEquals("서울 마포구 서교동 1길", location.getRoadAddress());
        assertEquals("서울 마포구 동교동 150-1", location.getJibunAddress());
        assertEquals(10.23f, location.getLatitude());
        assertEquals(32.99f, location.getLongitude());
    }

    @Test
    @DisplayName("이름으로 화장실 위치 1개 조회")
    void getToiletLocation() {
        // given
        LocationCreate requestLocation = LocationCreate
                .builder()
                .name("홍대 화장실")
                .roadAddress("서울 마포구 서교동 1길")
                .jibunAddress("서울 마포구 동교동 150-1")
                .latitude(10.23f)
                .longitude(32.99f)
                .build();
        locationService.add(requestLocation);

        // when
        LocationResponse response = locationService.get(requestLocation.name());

        // then
        assertNotNull(response);
        assertEquals(1L, locationRepository.count());
        assertEquals("홍대 화장실", response.name());
        assertEquals("서울 마포구 서교동 1길", response.roadAddress());
        assertEquals("서울 마포구 동교동 150-1", response.jibunAddress());
        assertEquals(10.23f, response.latitude());
        assertEquals(32.99f, response.longitude());
    }
}