package org.personal.info_service.service;

import org.personal.info_service.domain.ToiletInfo;
import org.personal.info_service.request.RequestCreateInfo;
import org.personal.info_service.response.ToiletInfoResponse;
import org.springframework.stereotype.Service;

public interface ToiletInfoService {
    ToiletInfoResponse createToiletInfo(RequestCreateInfo toiletInfo);
}
