package org.personal.locations_service.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.personal.locations_service.messaging.LocationEventProducer;
import org.personal.locations_service.request.LocationCreate;
import org.personal.locations_service.request.LocationEdit;
import org.personal.locations_service.request.LocationMarker;
import org.personal.locations_service.response.LocationResponse;
import org.personal.locations_service.service.LocationService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/locations")
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;
    private final LocationEventProducer locationEventProducer;

    @PostMapping
    public void add(@RequestBody @Valid LocationCreate locationCreate) {
        locationService.add(locationCreate);
    }

    @GetMapping("/{toiletName}")
    public ResponseEntity<LocationResponse> get(@PathVariable String toiletName){
        LocationResponse response = locationService.get(toiletName);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<LocationResponse>> getList(@ModelAttribute @Valid LocationMarker request) {
        request.validate();
        List<LocationResponse> response = locationService.getList(request);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @PatchMapping("/{locationId}")
    public void editLocation(@PathVariable Long locationId, @RequestBody @Valid LocationEdit request) {
        locationService.edit(locationId, request);
    }

    @DeleteMapping("/{locationId}")
    public void delete(@PathVariable Long locationId) { locationService.delete(locationId); }

    @GetMapping("/kafka")
    public String messageProduce(){
        locationEventProducer.asyncSend("toilet-location-deleted", "삭제됨");
        locationEventProducer.syncSend("toilet-location-deleted-sync", "삭제됨");
        return "Kafka service started";
    }
}
