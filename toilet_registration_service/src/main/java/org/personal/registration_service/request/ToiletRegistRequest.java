package org.personal.registration_service.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

public record ToiletRegistRequest(

	Long toiletRegistId,
	String toiletRegistDate,
	Boolean toiletRegistIsApproved,

	@NotNull(message = "위도는 필수 값입니다.")
	Double toiletRegistLatitude,

	@NotNull(message = "경도는 필수 값입니다.")
	Double toiletRegistLongitude

) {

	@Builder
	public ToiletRegistRequest {

	}
}
