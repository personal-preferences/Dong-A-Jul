package org.personal.registration_service.repository.impl;

import static org.personal.registration_service.domain.QToiletRegist.*;

import java.util.List;
import java.util.Optional;

import org.personal.registration_service.domain.QToiletRegist;
import org.personal.registration_service.domain.ToiletRegist;
import org.personal.registration_service.repository.QueryDSLRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Repository
public class QueryDSLRepositoryImpl implements QueryDSLRepository {

	private final JPAQueryFactory queryFactory;

	public QueryDSLRepositoryImpl(JPAQueryFactory queryFactory) {
		this.queryFactory = queryFactory;
	}

	@Override
	public Optional<ToiletRegist> findByToiletRegistId(long toiletRegistId) {
		ToiletRegist result = queryFactory
			.selectFrom(toiletRegist)
			.where(toiletRegist.toiletRegistId.eq(toiletRegistId))
			.fetchOne();

		return Optional.ofNullable(result);
	}

	@Override
	public Page<ToiletRegist> findAllByPageable(Pageable pageable) {
		OrderSpecifier<?> orderSpecifier = QToiletRegist.toiletRegist.toiletRegistConfirmedDate.asc().nullsFirst();

		List<ToiletRegist> results = queryFactory
			.selectFrom(toiletRegist)
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.orderBy(orderSpecifier)
			.fetch();

		long total = queryFactory
			.selectFrom(toiletRegist)
			.fetchCount();

		return new PageImpl<>(results, pageable, total);
	}
}

