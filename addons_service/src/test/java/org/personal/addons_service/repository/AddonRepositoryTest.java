package org.personal.addons_service.repository;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

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

	@Test
	public void testFindByUserEmailAndToiletLocationId() {

		// given
		Addon addon = Addon.builder()
			.memoContent("Test memo")
			.isBookmarked(true)
			.userEmail("test@example.com")
			.toiletLocationId(1L)
			.build();

		addonRepository.save(addon);

		// when
		Optional<Addon> retrievedAddon = addonRepository.findByUserEmailAndToiletLocationId("test@example.com", 1L);

		// then
		assertTrue(retrievedAddon.isPresent());
		assertEquals(addon.getAddonId(), retrievedAddon.get().getAddonId());
		assertEquals("Test memo", retrievedAddon.get().getMemoContent());
		assertTrue(retrievedAddon.get().isBookmarked());
		assertEquals("test@example.com", retrievedAddon.get().getUserEmail());
		assertEquals(1L, retrievedAddon.get().getToiletLocationId());
	}

	@Test
	public void testFindByUserEmailAndToiletLocationId_NotFound() {

		// given
		// No Addon is saved to the repository

		// when
		Optional<Addon> retrievedAddon = addonRepository.findByUserEmailAndToiletLocationId("nonexistent@example.com", 1L);

		// then
		assertFalse(retrievedAddon.isPresent());
	}


}
