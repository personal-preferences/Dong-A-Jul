package org.personal.addons_service.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.personal.addons_service.exception.AddonErrorResult;
import org.personal.addons_service.exception.AddonException;
import org.personal.addons_service.exception.GlobalExceptionHandler;
import org.personal.addons_service.request.CreateAddonRequest;
import org.personal.addons_service.request.GetAddonRequest;
import org.personal.addons_service.request.UpdateAddonRequest;
import org.personal.addons_service.response.AddonResponse;
import org.personal.addons_service.service.AddonServiceImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.google.gson.Gson;

@ExtendWith(MockitoExtension.class)
public class AddonControllerTest {

	private static final String BASE_URL = "/addons";
	private static final String CREATE_URL = BASE_URL + "/create";
	private static final String GET_URL = BASE_URL + "/get";
	private static final String TEST_USER_EMAIL = "test@example.com";
	private static final String WRONG_USER_EMAIL = "wrong@example.com";
	private static final Long TEST_TOILET_LOCATION_ID = 1L;
	private static final Long TEST_ADDON_ID = 1L;

	@Mock
	private AddonServiceImpl addonService;

	@InjectMocks
	private AddonController addonController;
	private MockMvc mockMvc;
	private Gson gson;

	@BeforeEach
	public void init() {
		gson = new Gson();
		mockMvc = MockMvcBuilders.standaloneSetup(addonController)
			.setControllerAdvice(new GlobalExceptionHandler())
			.build();
	}

	@Test
	public void 애드온생성실패__toiletLocationId누락오류() throws Exception {

		// given
		CreateAddonRequest request = CreateAddonRequest.builder()
			.userEmail(TEST_USER_EMAIL)
			.toiletLocationId(null)
			.memoContent("Test memo")
			.isBookmarked(false)
			.build();

		// when
		final ResultActions resultActions = mockMvc.perform(
			MockMvcRequestBuilders.post(CREATE_URL)
				.content(gson.toJson(request))
				.contentType(MediaType.APPLICATION_JSON)
		);

		// then
		resultActions
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.errorCode").value("VALIDATION_ERROR"))
			.andExpect(jsonPath("$.message").value("DTO 객체 @Valid 유효성 검사 실패"))
			.andExpect(jsonPath("$.fieldErrors.toiletLocationId").value("must not be null"));
	}

	@Test
	public void 애드온생성실패_userEmail누락오류() throws Exception {

		// given
		CreateAddonRequest request = CreateAddonRequest.builder()
			.userEmail(null)  // userEmail 필수값 누락
			.toiletLocationId(TEST_TOILET_LOCATION_ID)  // 정상값
			.memoContent("Test memo")
			.isBookmarked(false)
			.build();

		// when
		final ResultActions resultActions = mockMvc.perform(
			MockMvcRequestBuilders.post(CREATE_URL)
				.content(gson.toJson(request))
				.contentType(MediaType.APPLICATION_JSON)
		);

		// then
		resultActions
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.errorCode").value("VALIDATION_ERROR"))
			.andExpect(jsonPath("$.message").value("DTO 객체 @Valid 유효성 검사 실패"))
			.andExpect(jsonPath("$.fieldErrors.userEmail").value("must not be blank"));
	}

	@Test
	public void 애드온생성실패_필수값누락오류() throws Exception {

		// given
		CreateAddonRequest request = CreateAddonRequest.builder()
			.userEmail(null)  // 필수값 누락
			.toiletLocationId(null)  // 필수값 누락
			.memoContent("Test memo")
			.isBookmarked(false)
			.build();

		// when
		final ResultActions resultActions = mockMvc.perform(
			MockMvcRequestBuilders.post(CREATE_URL)
				.content(gson.toJson(request))
				.contentType(MediaType.APPLICATION_JSON)
		);

		// then
		resultActions
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.errorCode").value("VALIDATION_ERROR"))
			.andExpect(jsonPath("$.message").value("DTO 객체 @Valid 유효성 검사 실패"))
			.andExpect(jsonPath("$.fieldErrors.userEmail").value("must not be blank"))
			.andExpect(jsonPath("$.fieldErrors.toiletLocationId").value("must not be null"));
	}

	@Test
	public void 애드온생성성공() throws Exception {

		// given
		CreateAddonRequest request = CreateAddonRequest.builder()
			.userEmail(TEST_USER_EMAIL)
			.toiletLocationId(TEST_TOILET_LOCATION_ID)
			.memoContent("Test memo")
			.isBookmarked(false)
			.build();

		AddonResponse expectedResponse = AddonResponse.builder()
			.addonId(1L)
			.userEmail(TEST_USER_EMAIL)
			.toiletLocationId(TEST_TOILET_LOCATION_ID)
			.memoContent("Test memo")
			.isBookmarked(false)
			.build();

		when(addonService.createAddon(any(CreateAddonRequest.class))).thenReturn(expectedResponse);

		// when
		final ResultActions resultActions = mockMvc.perform(
			MockMvcRequestBuilders.post(CREATE_URL)
				.content(gson.toJson(request))
				.contentType(MediaType.APPLICATION_JSON)
		);

		// then
		resultActions
			.andExpect(status().isCreated())
			.andExpect(header().string("Location", BASE_URL + "/1"))
			.andExpect(jsonPath("$.addonId").value(1))
			.andExpect(jsonPath("$.userEmail").value(TEST_USER_EMAIL))
			.andExpect(jsonPath("$.toiletLocationId").value(TEST_TOILET_LOCATION_ID))
			.andExpect(jsonPath("$.memoContent").value("Test memo"))
			.andExpect(jsonPath("$.isBookmarked").value(false));
	}

	@Test
	public void 애드온생성실패_중복생성() throws Exception {

		// given
		CreateAddonRequest request = CreateAddonRequest.builder()
			.userEmail(TEST_USER_EMAIL)
			.toiletLocationId(TEST_TOILET_LOCATION_ID)
			.memoContent("Test memo")
			.isBookmarked(true)
			.build();

		// 중복 예외를 던지도록 서비스 계층을 모킹
		when(addonService.createAddon(any(CreateAddonRequest.class)))
			.thenThrow(new AddonException(AddonErrorResult.DUPLICATED_ADDON_CREATE));

		// when
		final ResultActions resultActions = mockMvc.perform(
			MockMvcRequestBuilders.post(CREATE_URL)
				.content(gson.toJson(request))
				.contentType(MediaType.APPLICATION_JSON)
		);

		// then
		resultActions
			.andExpect(status().isConflict())
			.andExpect(jsonPath("$.errorCode").value("DUPLICATED_ADDON_CREATE"))
			.andExpect(jsonPath("$.message").value(AddonErrorResult.DUPLICATED_ADDON_CREATE.getMessage()));
	}

	@Test
	public void 애드온조회성공() throws Exception {

		// given
		GetAddonRequest request = new GetAddonRequest(TEST_USER_EMAIL, TEST_TOILET_LOCATION_ID);

		AddonResponse expectedResponse = AddonResponse.builder()
			.addonId(1L)
			.userEmail(TEST_USER_EMAIL)
			.toiletLocationId(TEST_TOILET_LOCATION_ID)
			.memoContent("Test memo")
			.isBookmarked(false)
			.build();

		when(addonService.getAddon(any(GetAddonRequest.class))).thenReturn(expectedResponse);

		// when
		final ResultActions resultActions = mockMvc.perform(
			MockMvcRequestBuilders.post(GET_URL)
				.content(gson.toJson(request))
				.contentType(MediaType.APPLICATION_JSON)
		);

		// then
		resultActions
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.addonId").value(1))
			.andExpect(jsonPath("$.userEmail").value(TEST_USER_EMAIL))
			.andExpect(jsonPath("$.toiletLocationId").value(TEST_TOILET_LOCATION_ID))
			.andExpect(jsonPath("$.memoContent").value("Test memo"))
			.andExpect(jsonPath("$.isBookmarked").value(false));
	}

	@Test
	public void 애드온조회실패_ADDON_NOT_FOUND() throws Exception {

		// given
		GetAddonRequest request = new GetAddonRequest(TEST_USER_EMAIL, TEST_TOILET_LOCATION_ID);

		// 애드온을 찾을 수 없을 때 예외를 던지도록 서비스 계층 모킹
		when(addonService.getAddon(any(GetAddonRequest.class)))
			.thenThrow(new AddonException(AddonErrorResult.ADDON_NOT_FOUND));

		// when
		final ResultActions resultActions = mockMvc.perform(
			MockMvcRequestBuilders.post(GET_URL)
				.content(gson.toJson(request))
				.contentType(MediaType.APPLICATION_JSON)
		);

		// then
		resultActions
			.andExpect(status().isNotFound())
			.andExpect(jsonPath("$.errorCode").value("ADDON_NOT_FOUND"))
			.andExpect(jsonPath("$.message").value(AddonErrorResult.ADDON_NOT_FOUND.getMessage()));
	}

	@Test
	@DisplayName("수정 성공")
	public void testUpdateAddonSuccessfully() throws Exception {

		// given
		UpdateAddonRequest request = UpdateAddonRequest.builder()
			.memoContent("Updated memo")
			.isBookmarked(true)
			.build();

		AddonResponse expectedResponse = AddonResponse.builder()
			.addonId(TEST_ADDON_ID)
			.memoContent("Updated memo")
			.isBookmarked(true)
			.userEmail(TEST_USER_EMAIL)
			.toiletLocationId(TEST_TOILET_LOCATION_ID)
			.build();

		when(addonService.updateAddon(TEST_ADDON_ID, TEST_USER_EMAIL, request)).thenReturn(expectedResponse);

		// when
		final ResultActions resultActions = mockMvc.perform(patch(BASE_URL + "/" + TEST_ADDON_ID)
			.header("userEmail", TEST_USER_EMAIL)
			.contentType(MediaType.APPLICATION_JSON)
			.content(gson.toJson(request)));

		// then
		resultActions
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.addonId").value(TEST_ADDON_ID))
			.andExpect(jsonPath("$.memoContent").value("Updated memo"))
			.andExpect(jsonPath("$.isBookmarked").value(true))
			.andExpect(jsonPath("$.userEmail").value(TEST_USER_EMAIL))
			.andExpect(jsonPath("$.toiletLocationId").value(TEST_TOILET_LOCATION_ID));

		verify(addonService, times(1)).updateAddon(TEST_ADDON_ID, TEST_USER_EMAIL, request);
	}

	@Test
	@DisplayName("수정 실패_UNAUTHORIZED")
	public void testUpdateAddonUnauthorized() throws Exception {

		// given
		UpdateAddonRequest request = UpdateAddonRequest.builder()
			.memoContent("Updated memo")
			.isBookmarked(true)
			.build();

		when(addonService.updateAddon(TEST_ADDON_ID, WRONG_USER_EMAIL, request))
			.thenThrow(new AddonException(AddonErrorResult.UNAUTHORIZED_ACCESS));

		// when
		final ResultActions resultActions = mockMvc.perform(patch(BASE_URL + "/" + TEST_ADDON_ID)
			.header("userEmail", WRONG_USER_EMAIL)
			.contentType(MediaType.APPLICATION_JSON)
			.content(gson.toJson(request)));

		// then
		resultActions
			.andExpect(status().isUnauthorized())
			.andExpect(jsonPath("$.errorCode").value("UNAUTHORIZED_ACCESS"))
			.andExpect(jsonPath("$.message").value(AddonErrorResult.UNAUTHORIZED_ACCESS.getMessage()));
	}

	@Test
	@DisplayName("수정 실패_NOT FOUND")
	public void testUpdateAddonNotFound() throws Exception {

		// given
		UpdateAddonRequest request = UpdateAddonRequest.builder()
			.memoContent("Updated memo")
			.isBookmarked(true)
			.build();

		when(addonService.updateAddon(TEST_ADDON_ID, TEST_USER_EMAIL, request))
			.thenThrow(new AddonException(AddonErrorResult.ADDON_NOT_FOUND));

		// when
		final ResultActions resultActions = mockMvc.perform(patch(BASE_URL + "/" + TEST_ADDON_ID)
			.header("userEmail", TEST_USER_EMAIL)
			.contentType(MediaType.APPLICATION_JSON)
			.content(gson.toJson(request)));

		// then
		resultActions
			.andExpect(status().isNotFound())
			.andExpect(jsonPath("$.errorCode").value("ADDON_NOT_FOUND"))
			.andExpect(jsonPath("$.message").value(AddonErrorResult.ADDON_NOT_FOUND.getMessage()));
	}




}
