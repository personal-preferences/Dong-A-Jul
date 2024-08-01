package org.personal.info_service.repository;

import org.personal.info_service.domain.ToiletInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ToiletInfoRepository extends JpaRepository<ToiletInfo, Long>{
}
