package org.personal.addons_service.repository;

import org.personal.addons_service.domain.Addon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddonRepository extends JpaRepository<Addon, Long> {
}
