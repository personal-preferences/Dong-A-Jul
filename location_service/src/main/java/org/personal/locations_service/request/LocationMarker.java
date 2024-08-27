package org.personal.locations_service.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.personal.locations_service.exception.InvalidRequest;

public record LocationMarker(
        @NotNull(message = "북동쪽 위도를 입력하세요.")
        @Min(value = -90, message = "북동쪽 위도는 -90보다 크거나 같아야 합니다.")
        @Max(value = 90, message = "북동쪽 위도는 90보다 작거나 같아야 합니다.")
        Float northEastLatitude,

        @NotNull(message = "북동쪽 경도를 입력하세요.")
        @Min(value = -180, message = "북동쪽 경도는 -180보다 크거나 같아야 합니다.")
        @Max(value = 180, message = "북동쪽 경도는 180보다 작거나 같아야 합니다.")
        Float northEastLongitude,

        @NotNull(message = "남서쪽 위도를 입력하세요.")
        @Min(value = -90, message = "남서쪽 위도는 -90보다 크거나 같아야 합니다.")
        @Max(value = 90, message = "남서쪽 위도는 90보다 작거나 같아야 합니다.")
        Float southWestLatitude,

        @NotNull(message = "남서쪽 경도를 입력하세요.")
        @Min(value = -180, message = "남서쪽 경도는 -180보다 크거나 같아야 합니다.")
        @Max(value = 180, message = "남서쪽 경도는 180보다 작거나 같아야 합니다.")
        Float southWestLongitude
) {
    @Builder
    public LocationMarker {
    }

    public void validate() {
        if (northEastLatitude <= southWestLatitude ||
            northEastLongitude <= southWestLongitude) {
            throw new InvalidRequest("북동쪽 좌표는 남서쪽 좌표보다 커야 합니다.");
        }
    }
}
