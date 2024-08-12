package org.personal.registration_service.service;

import org.personal.registration_service.request.ToiletRegistApproveRequest;
import org.personal.registration_service.response.ToiletRegistApproveResponse;

public interface ToiletRegistApproveService {
	ToiletRegistApproveResponse updateToiletRegistApprove(ToiletRegistApproveRequest request);
}
