package org.personal.info_service.response;

import lombok.Builder;
import org.springframework.http.HttpStatus;

@Builder
public record ErrorResponse(
        int code,
        String message
) {
}
