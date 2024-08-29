package org.personal.locations_service.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.personal.locations_service.request.LocationCreate;
import org.personal.locations_service.request.LocationEdit;
import org.personal.locations_service.request.LocationMarker;
import org.personal.locations_service.response.LocationResponse;
import org.personal.locations_service.service.LocationService;
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

}
