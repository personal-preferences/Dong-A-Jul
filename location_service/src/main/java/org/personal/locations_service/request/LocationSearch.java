package org.personal.locations_service.request;

public record LocationSearch(
        Integer page,
        Integer size
) {
    private static final int MAX_SIZE = 2000;
}
