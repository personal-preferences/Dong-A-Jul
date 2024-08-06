package org.personal.registration_service.service;

import org.personal.registration_service.domain.ToiletRegist;

public interface ToiletRegistService {
	ToiletRegist addToiletRegist(final Double lat, final Double lng);
}
