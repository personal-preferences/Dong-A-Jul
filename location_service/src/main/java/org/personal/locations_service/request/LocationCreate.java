package org.personal.locations_service.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

public record LocationCreate(
        @NotBlank String name,
        @NotBlank String roadAddress,
        @NotBlank String jibunAddress,
        Float latitude,         // 위도
        Float longitude        // 경도
) {

    @Builder
    public LocationCreate(String name, String roadAddress, String jibunAddress, Float latitude, Float longitude) {
        this.name = name;
        this.roadAddress = roadAddress;
        this.jibunAddress = jibunAddress;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
