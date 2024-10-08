package org.personal.registration_service.service;

import org.personal.registration_service.request.ToiletRegistApproveRequest;
import org.personal.registration_service.response.ToiletRegistApproveResponse;
import org.personal.registration_service.response.ToiletRegistResponse;
import org.springframework.data.domain.Page;

public interface ToiletRegistApproveService {

	ToiletRegistApproveResponse updateToiletRegistApprove(ToiletRegistApproveRequest request);

	ToiletRegistResponse getToiletRegist(long toiletRegistId);

	Page<ToiletRegistResponse> listToiletRegist(int pageNum);

}
