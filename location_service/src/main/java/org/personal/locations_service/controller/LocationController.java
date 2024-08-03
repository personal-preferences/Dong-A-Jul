package org.personal.locations_service.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.personal.locations_service.request.LocationCreate;
import org.personal.locations_service.response.LocationResponse;
import org.personal.locations_service.service.LocationService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
    public LocationResponse get(@PathVariable String toiletName){
        return locationService.get(toiletName);
    }

    @GetMapping
    public List<LocationResponse> getList(@PageableDefault(sort = "id") Pageable pageable) {
        return locationService.getList(pageable);
    }
}
