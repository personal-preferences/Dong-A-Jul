package org.personal.locations_service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.personal.locations_service.domain.Location;
import org.personal.locations_service.exception.ToiletNotFound;
import org.personal.locations_service.repository.LocationRepository;
import org.personal.locations_service.request.LocationCreate;
import org.personal.locations_service.response.LocationResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService{

    private final LocationRepository locationRepository;

    @Override
    public void add(LocationCreate locationCreate) {
        Location location = Location.builder()
                .name(locationCreate.name())
                .roadAddress(locationCreate.roadAddress())
                .jibunAddress(locationCreate.jibunAddress())
                .latitude(locationCreate.latitude())
                .longitude(locationCreate.longitude())
                .build();

        locationRepository.save(location);
    }

    @Override
    public LocationResponse get(String toiletName) {
        Location location = locationRepository.findByName(toiletName)
                .orElseThrow(ToiletNotFound::new);

        return LocationResponse.builder()
                .id(location.getId())
                .name(location.getName())
                .roadAddress(location.getRoadAddress())
                .jibunAddress(location.getJibunAddress())
                .latitude(location.getLatitude())
                .longitude(location.getLongitude())
                .build();
    }

    @Override
    public List<LocationResponse> getList(Pageable pageable) {
        return locationRepository.getList(pageable).stream()
                .map(LocationResponse::new)
                .toList();
    }
}
