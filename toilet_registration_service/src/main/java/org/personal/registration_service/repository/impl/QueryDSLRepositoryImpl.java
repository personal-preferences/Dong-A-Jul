package org.personal.registration_service.repository.impl;

import static org.personal.registration_service.domain.QToiletRegist.*;

import java.util.Optional;

import org.personal.registration_service.domain.ToiletRegist;
import org.personal.registration_service.repository.QueryDSLRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

@Repository
public class QueryDSLRepositoryImpl implements QueryDSLRepository {

	@Autowired
	private final JPAQueryFactory queryFactory;

	public QueryDSLRepositoryImpl(JPAQueryFactory queryFactory) {
		this.queryFactory = queryFactory;
	}

	@Override
	public Optional<ToiletRegist> findBytoiletRegistId(long toiletRegistId) {
		ToiletRegist result = queryFactory
			.selectFrom(toiletRegist)
			.where(toiletRegist.toiletRegistId.eq(toiletRegistId))
			.fetchOne();

		return Optional.ofNullable(result);
	}
}
