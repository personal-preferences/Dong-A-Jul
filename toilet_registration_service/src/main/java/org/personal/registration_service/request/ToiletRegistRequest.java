package org.personal.registration_service.request;

import lombok.Builder;

@Builder
public record ToiletRegistRequest(
	Double toiletRegistLatitude,
	Double toiletRegistLongitude
) {
}
