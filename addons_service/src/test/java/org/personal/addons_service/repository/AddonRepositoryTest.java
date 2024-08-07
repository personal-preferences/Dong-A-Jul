package org.personal.addons_service.repository;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.personal.addons_service.domain.Addon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
public class AddonRepositoryTest {

	@Autowired
	private AddonRepository addonRepository;

	@Test
	public void testSaveAddon() {

		//given
		Addon addon = Addon.builder()
			.memoContent("Test memo")
			.isBookmarked(true)
			.userEmail("test@example.com")
			.toiletLocationId(1L)
			.build();

		//when
		Addon savedAddon = addonRepository.save(addon);

		//then
		assertNotNull(savedAddon.getAddonId());
		assertEquals("Test memo", savedAddon.getMemoContent());
		assertTrue(savedAddon.isBookmarked());
		assertEquals("test@example.com", savedAddon.getUserEmail());
		assertEquals(1L, savedAddon.getToiletLocationId());
	}
}
