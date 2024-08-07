package org.personal.addons_service.repository;

import java.util.Optional;

import org.personal.addons_service.domain.Addon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddonRepository extends JpaRepository<Addon, Long> {
	Optional<Addon> findByUserEmailAndToiletLocationId(String userEmail, Long toiletLocationId);
}
