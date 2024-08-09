package org.personal.info_service.repository;

import org.personal.info_service.domain.ToiletInfo;

import java.util.List;

public interface ToiletInfoRepositoryCustom {
    ToiletInfo findToiletInfo(Long locationId);
    List<ToiletInfo> findToiletInfoList(List<Long> locationIds);
    List<ToiletInfo> findAllWithDisabledToilets();
}
