package org.personal.locations_service.repository;

import org.personal.locations_service.domain.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LocationRepository extends JpaRepository<Location, Long>, LocationRepositoryCustom {
    Optional<Location> findByName(String toiletName);
}
