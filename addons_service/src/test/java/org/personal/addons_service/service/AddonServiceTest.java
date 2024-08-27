package org.personal.addons_service.service;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.personal.addons_service.domain.Addon;
import org.personal.addons_service.exception.AddonErrorResult;
import org.personal.addons_service.exception.AddonException;
import org.personal.addons_service.mapper.AddonMapper;
import org.personal.addons_service.repository.AddonRepository;
import org.personal.addons_service.request.CreateAddonRequest;
import org.personal.addons_service.request.GetAddonRequest;
import org.personal.addons_service.request.UpdateAddonRequest;
import org.personal.addons_service.response.AddonResponse;

@ExtendWith(MockitoExtension.class)
public class AddonServiceTest {

	@Mock
	private AddonRepository addonRepository;

	@InjectMocks
	private AddonServiceImpl addonService;

	@Mock
	private AddonMapper addonMapper;

	private final String userEmail = "test@example.com";
	private final Long locationId = 1L;
	private final Long addonId = 1L;
	private Addon existingAddon;

	@BeforeEach
	public void setUp() {
		existingAddon = Addon.builder()
			.memoContent("Existing memo")
			.isBookmarked(true)
			.userEmail(userEmail)
			.toiletLocationId(locationId)
			.build();
	}
	@Test
	@DisplayName("등록 실패_DUPLICATED")
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
	@DisplayName("조회 실패_NOT FOUND")
	public void testGetAddonNotFound() {

		// given
		GetAddonRequest request = new GetAddonRequest(userEmail, locationId);

		doReturn(Optional.empty()).when(addonRepository).findByUserEmailAndToiletLocationId(userEmail, locationId);

		// when
		AddonException result = assertThrows(AddonException.class, () -> addonService.getAddon(request));

		// then
		assertThat(result.getErrorResult()).isEqualTo(AddonErrorResult.ADDON_NOT_FOUND);
		verify(addonRepository, times(1)).findByUserEmailAndToiletLocationId(userEmail, locationId);
		verify(addonMapper, never()).toDto(any(Addon.class));
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

		Addon newAddon = Addon.builder()
			.addonId(1L)
			.memoContent("New memo")
			.isBookmarked(false)
			.userEmail(userEmail)
			.toiletLocationId(locationId)
			.build();

		when(addonMapper.toEntity(request)).thenReturn(newAddon);
		when(addonRepository.save(any(Addon.class))).thenReturn(newAddon);

		// when
		assertDoesNotThrow(() -> addonService.createAddon(request));

		// then
		verify(addonRepository, times(1)).findByUserEmailAndToiletLocationId(userEmail, locationId);
		verify(addonMapper, times(1)).toEntity(request);  // toEntity 호출 확인
		verify(addonRepository, times(1)).save(newAddon);
	}

	@Test
	@DisplayName("조회 성공")
	public void testGetAddonSuccessfully() {

		// given
		Addon addon = Addon.builder()
			.addonId(1L)
			.memoContent("Memo content")
			.isBookmarked(false)
			.userEmail(userEmail)
			.toiletLocationId(locationId)
			.build();

		GetAddonRequest request = new GetAddonRequest(userEmail, locationId);

		AddonResponse expectedResponse = AddonResponse.builder()
			.addonId(1L)
			.memoContent("Memo content")
			.isBookmarked(false)
			.userEmail(userEmail)
			.toiletLocationId(locationId)
			.build();

		when(addonRepository.findByUserEmailAndToiletLocationId(userEmail, locationId))
			.thenReturn(Optional.of(addon));
		when(addonMapper.toDto(addon)).thenReturn(expectedResponse);

		// when
		AddonResponse actualResponse = addonService.getAddon(request);

		// then
		verify(addonRepository, times(1)).findByUserEmailAndToiletLocationId(userEmail, locationId);
		verify(addonMapper, times(1)).toDto(any(Addon.class));

		assertEquals(expectedResponse.addonId(), actualResponse.addonId());
		assertEquals(expectedResponse.memoContent(), actualResponse.memoContent());
		assertEquals(expectedResponse.isBookmarked(), actualResponse.isBookmarked());
		assertEquals(expectedResponse.userEmail(), actualResponse.userEmail());
		assertEquals(expectedResponse.toiletLocationId(), actualResponse.toiletLocationId());
	}


	@Test
	@DisplayName("수정 성공")
	public void testUpdateAddonSuccessfully() {

		// given
		UpdateAddonRequest request = UpdateAddonRequest.builder()
			.memoContent("Updated memo")
			.isBookmarked(false)
			.build();

		Addon updatedAddon = existingAddon.toBuilder()
			.memoContent("Updated memo")
			.isBookmarked(false)
			.build();

		doReturn(Optional.of(existingAddon)).when(addonRepository).findById(addonId);
		doReturn(updatedAddon).when(addonRepository).save(any(Addon.class));
		doReturn(new AddonResponse(addonId, "Updated memo", false, userEmail, 1L))
			.when(addonMapper).toDto(updatedAddon);

		// when
		AddonResponse response = addonService.updateAddon(addonId, userEmail, request);

		// then
		ArgumentCaptor<Addon> captor = ArgumentCaptor.forClass(Addon.class);
		verify(addonRepository).save(captor.capture());
		Addon capturedAddon = captor.getValue();

		assertThat(capturedAddon.getMemoContent()).isEqualTo("Updated memo");
		assertThat(capturedAddon.isBookmarked()).isEqualTo(false);
		assertThat(response.memoContent()).isEqualTo("Updated memo");
		assertThat(response.isBookmarked()).isEqualTo(false);
	}

	@Test
	@DisplayName("수정 실패_잘못된 사용자")
	public void testUpdateAddonUnauthorized() {

		// given
		UpdateAddonRequest request = UpdateAddonRequest.builder()
			.memoContent("Updated memo")
			.isBookmarked(false)
			.build();

		when(addonRepository.findById(addonId)).thenReturn(Optional.of(existingAddon));

		// when
		AddonException exception = assertThrows(AddonException.class, () ->
			addonService.updateAddon(addonId, "wronguser@example.com", request));

		// then
		assertThat(exception.getErrorResult()).isEqualTo(AddonErrorResult.UNAUTHORIZED_ACCESS);
		verify(addonRepository, times(1)).findById(addonId);
		verify(addonRepository, never()).save(any(Addon.class));
	}

	@Test
	@DisplayName("수정 실패_Addon NOT FOUND")
	public void testUpdateAddonNotFound() {

		// given
		UpdateAddonRequest request = UpdateAddonRequest.builder()
			.memoContent("Updated memo")
			.isBookmarked(false)
			.build();

		when(addonRepository.findById(addonId)).thenReturn(Optional.empty());

		// when
		AddonException exception = assertThrows(AddonException.class, () ->
			addonService.updateAddon(addonId, userEmail, request));

		// then
		assertThat(exception.getErrorResult()).isEqualTo(AddonErrorResult.ADDON_NOT_FOUND);
		verify(addonRepository, times(1)).findById(addonId);
		verify(addonRepository, never()).save(any(Addon.class));
	}




}
