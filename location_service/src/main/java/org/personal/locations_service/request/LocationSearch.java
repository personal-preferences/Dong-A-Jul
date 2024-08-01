package org.personal.locations_service.request;

import static java.lang.Math.max;
import static java.lang.Math.min;

public record LocationSearch(
        Integer page,
        Integer size
) {
    private static final int MAX_SIZE = 2000;

    // record 클래스는 @Builder.Default 사용이 불가능하다
    public LocationSearch {
        if (page == null) { page = 1; }
        if (size == null) { size = 10; }
    }

    public long getOffset() {
        // 페이지 번호가 1보다 작은 경우 기본값 1
        return (long) max(1, page) * min(size, MAX_SIZE);
    }
}
