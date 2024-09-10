package org.personal.locations_service.message.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record LocationDeletedEvent(
        @NotNull Long locationId
) {
}
