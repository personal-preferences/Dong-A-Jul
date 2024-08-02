package org.personal.locations_service.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.personal.locations_service.domain.Location;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.personal.locations_service.domain.QLocation.location;

@RequiredArgsConstructor
public class LocationRepositoryImpl implements LocationRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Location> getList(Pageable pageable) {
        return jpaQueryFactory.selectFrom(location)
                .where(location.isDeleted.eq(false))
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .orderBy(location.id.desc())
                .fetch();
    }
}
