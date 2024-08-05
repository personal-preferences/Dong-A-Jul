package org.personal.locations_service.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

public record LocationMarker(
        @NotBlank(message = "북동쪽 위도를 입력하세요.") float northEastLatitude,
        @NotBlank(message = "북동쪽 경도를 입력하세요.") float northEastLongitude,
        @NotBlank(message = "서남쪽 위도를 입력하세요.") float southWestLatitude,
        @NotBlank(message = "서남쪽 경도를 입력하세요.") float southWestLongitude
) {
    @Builder
    public LocationMarker {
    }
}
