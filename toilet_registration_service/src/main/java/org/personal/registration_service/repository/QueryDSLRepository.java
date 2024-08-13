package org.personal.registration_service.repository;

import java.util.Optional;

import org.personal.registration_service.domain.ToiletRegist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QueryDSLRepository {

	Optional<ToiletRegist> findByToiletRegistId(long toiletRegistId);

	Page<ToiletRegist> findAllByPageable(Pageable pageable);
}

