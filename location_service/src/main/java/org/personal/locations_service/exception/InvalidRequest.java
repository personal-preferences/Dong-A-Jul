package org.personal.locations_service.exception;

import lombok.Getter;

@Getter
public class InvalidRequest extends LocationException {

    private static final String MESSAGE = "잘못된 요청입니다.";

    public InvalidRequest() {
        super(MESSAGE);
    }

    public InvalidRequest(String message) {super(message);}

    public InvalidRequest(String filedName, String message) {
        super(MESSAGE);
        addValidation(filedName, message);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}
