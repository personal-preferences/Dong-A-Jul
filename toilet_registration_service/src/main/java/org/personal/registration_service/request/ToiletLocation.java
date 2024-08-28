package org.personal.registration_service.request;

import java.io.Serializable;

import lombok.Builder;

@Builder
public record ToiletLocation(
	Long toiletRegistId,
	String name,
	String roadAddress,
	String jibunAddress,
	Double latitude,         // 위도
	Double longitude        // 경도
) {
}
