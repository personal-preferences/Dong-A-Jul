package org.personal.locations_service.messaging.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record LocationDeletedEvent(
        @NotNull Long locationId
) {
}
