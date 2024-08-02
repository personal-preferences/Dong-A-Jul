package org.personal.locations_service.service;


import org.personal.locations_service.request.LocationCreate;
import org.personal.locations_service.request.LocationEdit;
import org.personal.locations_service.response.LocationResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LocationService {

    void add(LocationCreate locationCreate);

    LocationResponse get(String toiletName);

    List<LocationResponse> getList(Pageable pageable);

    void edit(Long id, LocationEdit request);
}