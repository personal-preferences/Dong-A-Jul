package org.personal.registration_service.repository.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.personal.registration_service.domain.QToiletRegist;
import org.personal.registration_service.domain.ToiletRegist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import static org.mockito.Mockito.*;
import static org.personal.registration_service.domain.QToiletRegist.*;

@ExtendWith(MockitoExtension.class)
class QueryDSLRepositoryImplTests {

	@InjectMocks
	private QueryDSLRepositoryImpl queryDSLRepositoryImpl;

	@Mock
	private JPAQueryFactory queryFactory;

	@Mock
	private JPAQuery<ToiletRegist> jpaQuery;

	@Test
	public void findAllByPageable_성공() {
		// given
		ToiletRegist toiletRegist1 = ToiletRegist.builder()
			.toiletRegistId(1L)
			.toiletRegistToiletName("화장실 1")
			.build();

		ToiletRegist toiletRegist2 = ToiletRegist.builder()
			.toiletRegistId(2L)
			.toiletRegistToiletName("화장실 2")
			.build();

		List<ToiletRegist> toiletRegists = Arrays.asList(toiletRegist1, toiletRegist2);
		Pageable pageable = PageRequest.of(0, 10);

		// 모의 데이터 설정
		OrderSpecifier<?> orderSpecifier = QToiletRegist.toiletRegist.toiletRegistConfirmedDate.asc().nullsFirst();

		when(queryFactory.selectFrom(toiletRegist)).thenReturn(jpaQuery);
		when(jpaQuery.offset(pageable.getOffset())).thenReturn(jpaQuery);
		when(jpaQuery.limit(pageable.getPageSize())).thenReturn(jpaQuery);
		when(jpaQuery.orderBy(orderSpecifier)).thenReturn(jpaQuery);  // 명시적인 orderSpecifier 사용
		when(jpaQuery.fetch()).thenReturn(toiletRegists);
		when(jpaQuery.fetchCount()).thenReturn((long) toiletRegists.size());

		// when
		Page<ToiletRegist> result = queryDSLRepositoryImpl.findAllByPageable(pageable);

		// then
		assertThat(result).isNotNull();
		assertThat(result.getContent()).hasSize(2);
		assertThat(result.getContent().get(0).getToiletRegistId()).isEqualTo(1L);
		assertThat(result.getContent().get(1).getToiletRegistId()).isEqualTo(2L);
	}

	@Test
	public void findAllByPageable_실패_빈결과() {
		// given
		Pageable pageable = PageRequest.of(0, 10);
		List<ToiletRegist> emptyList = Arrays.asList();

		// `OrderSpecifier` 명시적 생성
		OrderSpecifier<?> orderSpecifier = QToiletRegist.toiletRegist.toiletRegistConfirmedDate.asc().nullsFirst();

		// mocking
		when(queryFactory.selectFrom(toiletRegist)).thenReturn(jpaQuery);
		when(jpaQuery.offset(pageable.getOffset())).thenReturn(jpaQuery);
		when(jpaQuery.limit(pageable.getPageSize())).thenReturn(jpaQuery);
		when(jpaQuery.orderBy(orderSpecifier)).thenReturn(jpaQuery);  // 명시적으로 `orderSpecifier`를 사용
		when(jpaQuery.fetch()).thenReturn(emptyList);
		when(jpaQuery.fetchCount()).thenReturn(0L);

		// when
		Page<ToiletRegist> result = queryDSLRepositoryImpl.findAllByPageable(pageable);

		// then
		assertThat(result).isNotNull();
		assertThat(result.getContent()).isEmpty();
	}

}
