package org.personal.addons_service.service;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.personal.addons_service.domain.Addon;
import org.personal.addons_service.exception.AddonErrorResult;
import org.personal.addons_service.exception.AddonException;
import org.personal.addons_service.repository.AddonRepository;
import org.personal.addons_service.request.CreateAddonRequest;

@ExtendWith(MockitoExtension.class)
public class AddonServiceTest {

	@Mock
	private AddonRepository addonRepository;

	@InjectMocks
	private AddonServiceImpl addonService;

	private final String userEmail = "test@example.com";
	private final Long locationId = 1L;
	private Addon existingAddon;

	@BeforeEach
	public void setUp() {
		existingAddon = Addon.builder()
			.memoContent("Existing memo")
			.isBookmarked(true)
			.userEmail("test@example.com")
			.toiletLocationId(1L)
			.build();
	}
	@Test
	@DisplayName("중복 시 실패")
	public void testCreateAddonAlreadyExists() {

		// given
		doReturn(Optional.of(existingAddon)).when(addonRepository).findByUserEmailAndToiletLocationId(userEmail, locationId);

		CreateAddonRequest request = new CreateAddonRequest(
			"New memo",
			false,
			userEmail,
			locationId
		);

		// when
		AddonException result = assertThrows(AddonException.class, () -> addonService.createAddon(request));

		// then
		assertThat(result.getErrorResult()).isEqualTo(AddonErrorResult.DUPLICATED_ADDON_CREATE);
	}

	@Test
	@DisplayName("등록 성공")
	public void testCreateAddonSuccessfully() {

		// given
		doReturn(Optional.empty()).when(addonRepository).findByUserEmailAndToiletLocationId(userEmail, locationId);

		CreateAddonRequest request = new CreateAddonRequest(
			"New memo",
			false,
			userEmail,
			locationId
		);

		// 반환할 Addon 객체 생성
		Addon newAddon = Addon.builder()
			.addonId(1L)
			.memoContent("New memo")
			.isBookmarked(false)
			.userEmail(userEmail)
			.toiletLocationId(locationId)
			.build();

		doReturn(newAddon).when(addonRepository).save(any(Addon.class));

		// when
		assertDoesNotThrow(() -> addonService.createAddon(request));

		// then
		verify(addonRepository, times(1)).findByUserEmailAndToiletLocationId(userEmail, locationId);
		verify(addonRepository, times(1)).save(any(Addon.class));
	}







}
