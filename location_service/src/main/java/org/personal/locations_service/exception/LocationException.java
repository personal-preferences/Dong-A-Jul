package org.personal.locations_service.exception;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public abstract class LocationException extends RuntimeException {

    public final Map<String, String> validation = new HashMap<>();

    public LocationException(String message) {
        super(message);
    }

    public LocationException(String message, Throwable cause) {
        super(message, cause);
    }

    public abstract int getStatusCode();

    public void addValidation(String filedName, String errorMessage) {
        validation.put(filedName, errorMessage);
    }
}
