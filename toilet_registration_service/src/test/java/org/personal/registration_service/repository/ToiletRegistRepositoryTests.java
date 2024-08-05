package org.personal.registration_service.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class ToiletRegistRepositoryTests {

	@Autowired
	private ToiletRegistRepository toiletRegistRepository;

	@Test
	public void ToiletRegistRepository가Null이아님(){
		assertThat(toiletRegistRepository).isNotNull();
	}

}