package org.personal.locations_service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.personal.locations_service.domain.Location;
import org.personal.locations_service.exception.ToiletNotFound;
import org.personal.locations_service.messaging.LocationEventProducer;
import org.personal.locations_service.messaging.dto.LocationDeletedEvent;
import org.personal.locations_service.repository.LocationRepository;
import org.personal.locations_service.request.LocationCreate;
import org.personal.locations_service.request.LocationEdit;
import org.personal.locations_service.request.LocationMarker;
import org.personal.locations_service.response.LocationResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService{

    private final LocationRepository locationRepository;
    private final LocationEventProducer locationEventProducer;

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
        Location location = locationRepository.findByNameAndIsDeletedFalse(toiletName)
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
    public List<LocationResponse> getList(LocationMarker request) {
        return locationRepository.getList(request).stream()
                .map(LocationResponse::new)
                .toList();
    }

    @Override
    @Transactional
    public void edit(Long id, LocationEdit request) {
        Location location = locationRepository.findById(id)
                .orElseThrow(ToiletNotFound::new);

        location.edit(request);
    }

    @Override
    public void delete(Long id) {
        Location location = locationRepository.findById(id).orElseThrow(ToiletNotFound::new);

        locationRepository.delete(location);

        // Kafka 메시지 발행
        LocationDeletedEvent locationDeletedEvent = LocationDeletedEvent.builder()
                .locationId(location.getId())
                .build();

        locationEventProducer.send("toilet-location-deleted", locationDeletedEvent);
    }
}
