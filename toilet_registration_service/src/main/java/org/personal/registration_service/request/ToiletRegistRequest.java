package org.personal.registration_service.request;

import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
public record ToiletRegistRequest(

	@NotNull
	Double toiletRegistLatitude,

	@NotNull
	Double toiletRegistLongitude
) {
}
