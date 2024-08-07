package org.personal.info_service.service;

import org.personal.info_service.domain.ToiletInfo;
import org.personal.info_service.request.RequestCreateInfo;
import org.personal.info_service.response.ToiletInfoResponse;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ToiletInfoService {
    ToiletInfoResponse createToiletInfo(RequestCreateInfo toiletInfo);

    ToiletInfoResponse updateToiletInfo(RequestCreateInfo modifyToiletInfo);

    void deleteToiletinfo(Long locationId);

    ToiletInfoResponse getToiletInfo(Long locationId);

    List<ToiletInfoResponse> getToiletInfoList(List<Long> idList);

    List<ToiletInfoResponse> getDisabledToilets();
}
