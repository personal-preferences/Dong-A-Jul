package org.personal.registration_service.request;

import org.personal.registration_service.domain.ToiletRegist;

import lombok.Builder;

@Builder
public record ToiletLocationRequest(
	Long toiletRegistId,
	String name,
	String roadAddress,
	String jibunAddress,
	Double latitude,         // 위도
	Double longitude        // 경도
) {
	public static ToiletLocationRequest of(ToiletRegist toiletRegist){
		return ToiletLocationRequest.builder()
			.toiletRegistId(toiletRegist.getToiletRegistId())
			.name(toiletRegist.getToiletRegistToiletName())
			.roadAddress(toiletRegist.getToiletRegistRoadNameAddress())
			.jibunAddress(toiletRegist.getToiletRegistNumberAddress())
			.latitude(toiletRegist.getToiletRegistLatitude())
			.longitude(toiletRegist.getToiletRegistLongitude())
			.build();
	}
}
