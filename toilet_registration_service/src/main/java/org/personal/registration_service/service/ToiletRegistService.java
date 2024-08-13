package org.personal.registration_service.service;

import org.personal.registration_service.request.ToiletRegistRequest;
import org.personal.registration_service.response.ToiletRegistResponse;

public interface ToiletRegistService {
	ToiletRegistResponse addToiletRegist(ToiletRegistRequest toiletRegistRequest);
}
