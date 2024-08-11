package org.personal.locations_service.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.personal.locations_service.domain.Location;
import org.personal.locations_service.exception.ToiletNotFound;
import org.personal.locations_service.repository.LocationRepository;
import org.personal.locations_service.request.LocationCreate;
import org.personal.locations_service.request.LocationEdit;
import org.personal.locations_service.request.LocationMarker;
import org.personal.locations_service.response.LocationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.stream.IntStream;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

    @Test
    @DisplayName("이름으로 화장실 위치 1개 조회 - 존재하지 않는 화장실")
    void getToiletLocationNotFound() {
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
        String nonExistToilet = "없는 화장실";

        // then
        assertThrows(ToiletNotFound.class, () -> {
            locationService.get(nonExistToilet);
        });
    }

    @Test
    @DisplayName("지도 범위 내에 있는 화장실 여러개 조회")
    void getLocationList() throws Exception {
        // given
        List<Location> locationList = IntStream.range(0, 30)
                .mapToObj(i -> Location.builder()
                        .name("홍대 화장실" + i)
                        .roadAddress("서울 마포구 서교동 1길" + i)
                        .jibunAddress("서울 마포구 동교동 150-1" + i)
                        .latitude(10.23f + i)
                        .longitude(32.99f + i)
                        .build())
                .toList();
        locationRepository.saveAll(locationList);

        LocationMarker request = LocationMarker.builder()
                .southWestLatitude(10.23f)
                .northEastLatitude(15.23f)
                .southWestLongitude(32.99f)
                .northEastLongitude(37.99f)
                .build();

        // when
        List<LocationResponse> responseList = locationService.getList(request);

        // then
        assertEquals(6, responseList.size());
        assertEquals("홍대 화장실0", responseList.get(0).name());
        assertEquals("서울 마포구 서교동 1길0", responseList.get(0).roadAddress());
        assertEquals("서울 마포구 동교동 150-10", responseList.get(0).jibunAddress());
        assertEquals(10.23f, responseList.get(0).latitude());
        assertEquals(32.99f, responseList.get(0).longitude());
    }

    @Test
    @DisplayName("화장실 위치 수정")
    void editLocation() throws Exception {
        // given
        Location location = Location.builder()
                .name("홍대 화장실")
                .roadAddress("서울 마포구 서교동 1길")
                .jibunAddress("서울 마포구 동교동 150-1")
                .latitude(10.23f)
                .longitude(32.99f)
                .build();
        locationRepository.save(location);

        LocationEdit locationEdit = LocationEdit.builder()
                .name("한강진역 화장실")
                .roadAddress("서울 용산구 청파로 93길 27")
                .jibunAddress("서울 용산구 서계동 227-1")
                .latitude(99.11f)
                .longitude(55.89f)
                .build();

        // when
        locationService.edit(location.getId(), locationEdit);

        // then
        Location edittedLocation = locationRepository.findById(location.getId()).orElseThrow(ToiletNotFound::new);
        assertEquals("한강진역 화장실", edittedLocation.getName());
        assertEquals("서울 용산구 청파로 93길 27", edittedLocation.getRoadAddress());
        assertEquals("서울 용산구 서계동 227-1", edittedLocation.getJibunAddress());
        assertEquals(99.11f, edittedLocation.getLatitude());
        assertEquals(55.89f, edittedLocation.getLongitude());
    }

    @Test
    @DisplayName("화장실 위치 삭제")
    void deleteLocation() throws Exception {
        // given
        Location location = Location.builder()
                .name("홍대 화장실")
                .roadAddress("서울 마포구 서교동 1길")
                .jibunAddress("서울 마포구 동교동 150-1")
                .latitude(10.23f)
                .longitude(32.99f)
                .build();
        locationRepository.save(location);

        // when
        locationService.delete(location.getId());

        // then
        assertEquals(0L, locationRepository.count());
    }
}