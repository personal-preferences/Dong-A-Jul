package org.personal.locations_service.service;


import org.personal.locations_service.request.LocationCreate;
import org.personal.locations_service.request.LocationSearch;
import org.personal.locations_service.response.LocationResponse;

import java.util.List;

public interface LocationService {

    void add(LocationCreate locationCreate);

    LocationResponse get(String toiletName);

    List<LocationResponse> getList(LocationSearch locationSearch);
}
