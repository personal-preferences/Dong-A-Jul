package org.personal.registration_service.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.personal.registration_service.domain.ToiletRegist;
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

	@Test
	public void 화장실등록신청등록테스트() {
		// given
		final ToiletRegist toiletRegist = ToiletRegist.builder()
			.toiletRegistImg("sample_image.png")
			.toiletRegistToiletName("Sample Toilet")
			.toiletRegistRoadNameAddress("123 Sample Road")
			.toiletRegistNumberAddress("456")
			.toiletRegistLatitude(37.123456)
			.toiletRegistLongitude(127.123456)
			.build();

		// when
		final ToiletRegist result = toiletRegistRepository.save(toiletRegist);

		// then
		assertThat(result.getToiletRegistId()).isNotNull();
		assertThat(result.getToiletRegistDate()).isEqualTo(toiletRegist.getToiletRegistDate());
		assertThat(result.getToiletRegistImg()).isEqualTo(toiletRegist.getToiletRegistImg());
		assertThat(result.getToiletRegistIsApproved()).isEqualTo(toiletRegist.getToiletRegistIsApproved());
		assertThat(result.getToiletRegistToiletName()).isEqualTo(toiletRegist.getToiletRegistToiletName());
		assertThat(result.getToiletRegistRoadNameAddress()).isEqualTo(toiletRegist.getToiletRegistRoadNameAddress());
		assertThat(result.getToiletRegistNumberAddress()).isEqualTo(toiletRegist.getToiletRegistNumberAddress());
		assertThat(result.getToiletRegistLatitude()).isEqualTo(toiletRegist.getToiletRegistLatitude());
		assertThat(result.getToiletRegistLongitude()).isEqualTo(toiletRegist.getToiletRegistLongitude());
	}

	@Test
	public void 등록신청된화장실인지테스트(){
		// given
		final ToiletRegist toiletRegist = ToiletRegist.builder()
			.toiletRegistImg("sample_image.png")
			.toiletRegistToiletName("Sample Toilet")
			.toiletRegistRoadNameAddress("123 Sample Road")
			.toiletRegistNumberAddress("456")
			.toiletRegistLatitude(37.123456)
			.toiletRegistLongitude(127.123456)
			.build();

		// when
		toiletRegistRepository.save(toiletRegist);
		final ToiletRegist findResult = toiletRegistRepository
			.findByToiletRegistLatitudeAndToiletRegistLongitude(37.123456, 127.123456);

		// then
		assertThat(findResult).isNotNull();
		assertThat(findResult.getToiletRegistId()).isNotNull();
		assertThat(findResult.getToiletRegistDate()).isEqualTo(toiletRegist.getToiletRegistDate());
		assertThat(findResult.getToiletRegistImg()).isEqualTo(toiletRegist.getToiletRegistImg());
		assertThat(findResult.getToiletRegistIsApproved()).isEqualTo(toiletRegist.getToiletRegistIsApproved());
		assertThat(findResult.getToiletRegistToiletName()).isEqualTo(toiletRegist.getToiletRegistToiletName());
		assertThat(findResult.getToiletRegistRoadNameAddress()).isEqualTo(toiletRegist.getToiletRegistRoadNameAddress());
		assertThat(findResult.getToiletRegistNumberAddress()).isEqualTo(toiletRegist.getToiletRegistNumberAddress());
		assertThat(findResult.getToiletRegistLatitude()).isEqualTo(toiletRegist.getToiletRegistLatitude());
		assertThat(findResult.getToiletRegistLongitude()).isEqualTo(toiletRegist.getToiletRegistLongitude());
	}
}