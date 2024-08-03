package org.personal.locations_service.repository;

import org.personal.locations_service.domain.Location;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LocationRepositoryCustom {

    List<Location> getList(Pageable pageable);
}
