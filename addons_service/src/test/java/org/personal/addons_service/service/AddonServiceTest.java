package org.personal.addons_service.service;

import static org.personal.addons_service.domain.QAddon.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.personal.addons_service.domain.Addon;
import org.personal.addons_service.repository.AddonRepository;
import org.personal.addons_service.request.CreateAddonRequest;

@ExtendWith(MockitoExtension.class)
public class AddonServiceTest {

	@Mock
	private AddonRepository addonRepository;

	@InjectMocks
	private AddonService addonService;

	@Test
	@DisplayName("CreateAddon")
	public void CreateAddonTest() {

		// given
		CreateAddonRequest request = CreateAddonRequest.builder()
			.memoContent("Test memo")
			.isBookmarked(true)
			.userEmail("test@example.com")
			.toiletLocationId(1L)
			.build();

		// when
		addonService.createAddon(request);

		// then



	}


}
