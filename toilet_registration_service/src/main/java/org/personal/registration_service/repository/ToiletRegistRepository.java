package org.personal.registration_service.repository;

import org.personal.registration_service.domain.ToiletRegist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ToiletRegistRepository extends JpaRepository<ToiletRegist, Long> {
}

