package org.personal.locations_service.repository;

import org.personal.locations_service.domain.Location;
import org.personal.locations_service.request.LocationSearch;

import java.util.List;

public interface LocationRepositoryCustom {

    List<Location> getList(LocationSearch locationSearch);
}
