package org.personal.locations_service.exception;

import lombok.Getter;

/**
 * status -> 404
 */
@Getter
public class ToiletNotFound extends LocationException {

    private static final String MESSAGE = "존재하지 않는 화장실입니다.";

    public ToiletNotFound() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 404;
    }
}
