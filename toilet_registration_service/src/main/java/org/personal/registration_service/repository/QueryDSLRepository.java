package org.personal.registration_service.repository;

import java.util.Optional;

import org.personal.registration_service.domain.ToiletRegist;

public interface QueryDSLRepository {

	Optional<ToiletRegist> findBytoiletRegistId(long toiletRegistId);
}
