package org.personal.locations_service.response;

import lombok.Builder;

public record LocationResponse(
        Long id,
        String name,
        String roadAddress,
        String jibunAddress,
        float latitude,         // 위도
        float longitude        // 경도
) {

    @Builder
    public LocationResponse(Long id, String name, String roadAddress, String jibunAddress, float latitude, float longitude) {
        this.id = id;
        this.name = name;
        this.roadAddress = roadAddress;
        this.jibunAddress = jibunAddress;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
