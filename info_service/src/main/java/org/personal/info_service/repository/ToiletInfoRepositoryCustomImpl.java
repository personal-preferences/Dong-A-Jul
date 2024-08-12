package org.personal.info_service.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.personal.info_service.domain.QToiletInfo;
import org.personal.info_service.domain.ToiletInfo;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;

@RequiredArgsConstructor
public class ToiletInfoRepositoryCustomImpl implements ToiletInfoRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public ToiletInfo findToiletInfo(Long locationId){

        QToiletInfo toilet = QToiletInfo.toiletInfo;

        return jpaQueryFactory.selectFrom(toilet)
                .where(toilet.toiletLocationId.eq(locationId))
                .fetchOne();
    }

    @Override
    public List<ToiletInfo> findToiletInfoList(List<Long> locationIds) {

        QToiletInfo toilet = QToiletInfo.toiletInfo;

        return jpaQueryFactory
                .selectFrom(toilet)
                .where(toilet.toiletLocationId.in(locationIds))
                .fetch();
    }

    @Override
    public List<ToiletInfo> findAllWithDisabledToilets() {

        QToiletInfo toiletInfo = QToiletInfo.toiletInfo;

        return jpaQueryFactory
                .selectFrom(toiletInfo)
                .where(
                        toiletInfo.toiletInfoMaleDisabledToiletsNumber.gt(0)
                                .or(toiletInfo.toiletInfoMaleDisabledUrinalsNumber.gt(0))
                                .or(toiletInfo.toiletInfoFemaleDisabledToiletsNumber.gt(0))
                )
                .fetch();
    }
}
