package org.personal.registration_service.request;

import jakarta.validation.constraints.AssertFalse;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Builder;

@Builder
public record ToiletRegistApproveRequest(

	@NotNull(message = "toiletRegistId는 필수 값입니다.")
	Long toiletRegistId,

	@NotNull(message = "화장실 등록 신청 날짜는 필수 값입니다.")
	String toiletRegistDate,

	@NotNull(message = "등록 여부는 필수 값입니다.")
	@AssertFalse(message = "아직 관리자가 승인하지 않은 신청이어야 합니다.")
	Boolean toiletRegistIsApproved,

	@Null(message = "관리자가 승인하지 않아 승인 날짜는 항상 NULL이어야 합니다.")
	String toiletRegistConfirmedDate,

	@NotNull(message = "위도는 필수 값입니다.")
	Double toiletRegistLatitude,

	@NotNull(message = "경도는 필수 값입니다.")
	Double toiletRegistLongitude
) {

}
