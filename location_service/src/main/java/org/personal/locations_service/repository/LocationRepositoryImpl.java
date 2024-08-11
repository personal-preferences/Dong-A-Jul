package org.personal.locations_service.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.personal.locations_service.domain.Location;
import org.personal.locations_service.request.LocationMarker;

import java.util.List;

import static org.personal.locations_service.domain.QLocation.location;

@RequiredArgsConstructor
public class LocationRepositoryImpl implements LocationRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Location> getList(LocationMarker request) {
        return jpaQueryFactory.selectFrom(location)
                .where(
                        location.latitude.between(request.southWestLatitude(), request.northEastLatitude()),
                        location.longitude.between(request.southWestLongitude(), request.northEastLongitude())
                )
                .fetch();
    }
}
