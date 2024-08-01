package org.personal.locations_service.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

public record LocationCreate(
        @NotBlank(message = "화장실 이름을 입력하세요.") String name,
        @NotBlank(message = "도로명 주소를 입력하세요.") String roadAddress,
        @NotBlank(message = "지번 주소를 입력하세요.") String jibunAddress,
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
