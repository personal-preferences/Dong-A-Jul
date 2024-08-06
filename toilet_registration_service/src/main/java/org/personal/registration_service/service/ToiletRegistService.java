package org.personal.registration_service.service;

import org.personal.registration_service.response.ToiletRegistResponse;

public interface ToiletRegistService {
	ToiletRegistResponse addToiletRegist(final Double lat, final Double lng);
}
