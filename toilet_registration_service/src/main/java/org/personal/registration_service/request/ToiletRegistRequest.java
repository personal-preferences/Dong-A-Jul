package org.personal.registration_service.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

public record ToiletRegistRequest(

	Long toiletRegistId,
	String toiletRegistDate,
	Boolean toiletRegistIsApproved,

	@NotNull
	Double toiletRegistLatitude,

	@NotNull
	Double toiletRegistLongitude

) {

	@Builder
	public ToiletRegistRequest {

	}
}
