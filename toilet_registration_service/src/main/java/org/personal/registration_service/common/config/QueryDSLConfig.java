package org.personal.registration_service.common.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;

@Configuration
public class QueryDSLConfig {

	@PersistenceUnit
	private EntityManagerFactory entityManagerFactory;

	@Bean
	public JPAQueryFactory jpaQueryFactory() {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		return new JPAQueryFactory(entityManager);
	}
}
