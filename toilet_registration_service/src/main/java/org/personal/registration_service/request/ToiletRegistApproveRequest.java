package org.personal.registration_service.request;

import jakarta.validation.constraints.AssertFalse;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Builder;

@Builder
public record ToiletRegistApproveRequest(

	@NotNull(message = "toiletRegistId는 필수 값입니다.")
	Long toiletRegistId,

	@NotNull(message = "승인 여부를 확인해주세요.")
	Boolean toiletRegistIsApproved,

	@Null(message = "이미 확인된 신청입니다.")
	String toiletRegistConfirmedDate
) {

}
