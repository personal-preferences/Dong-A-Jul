package org.personal.locations_service.response;

import lombok.Builder;
import org.personal.locations_service.domain.Location;

public record LocationResponse(
        Long id,
        String name,
        String roadAddress,
        String jibunAddress,
        float latitude,         // 위도
        float longitude        // 경도
) {

    @Builder
    public LocationResponse {
    }

    public LocationResponse(Location location) {
        this(location.getId(), location.getName(), location.getRoadAddress(), location.getJibunAddress(), location.getLatitude(), location.getLongitude());
    }
}
