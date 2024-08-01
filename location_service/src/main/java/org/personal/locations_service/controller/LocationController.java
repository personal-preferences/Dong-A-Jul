package org.personal.locations_service.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.personal.locations_service.response.LocationResponse;
import org.personal.locations_service.service.LocationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/locations")
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;

    @GetMapping("/{toiletName}")
    public LocationResponse get(@PathVariable String toiletName){
        return locationService.get(toiletName);
    }

    @GetMapping
    public void getLocations() {

    }
}
