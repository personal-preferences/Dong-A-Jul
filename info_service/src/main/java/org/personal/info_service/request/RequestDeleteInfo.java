package org.personal.info_service.request;

import lombok.Builder;

@Builder
public record RequestDeleteInfo(
        Long locationId
) {}
