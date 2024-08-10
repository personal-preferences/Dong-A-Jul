package org.personal.addons_service.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.personal.addons_service.domain.Addon;
import org.personal.addons_service.exception.AddonErrorResult;
import org.personal.addons_service.exception.AddonException;
import org.personal.addons_service.exception.GlobalExceptionHandler;
import org.personal.addons_service.request.CreateAddonRequest;
import org.personal.addons_service.response.AddonCreateResponse;
import org.personal.addons_service.service.AddonServiceImpl;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

@ExtendWith(MockitoExtension.class)
public class AddonControllerTest {

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
		final String url = "/addons";
		CreateAddonRequest request = CreateAddonRequest.builder()
			.userEmail("test@example.com")
			.toiletLocationId(null)
			.memoContent("Test memo")
			.isBookmarked(false)
			.build();

		// when
		final ResultActions resultActions = mockMvc.perform(
			MockMvcRequestBuilders.post(url)
				.content(gson.toJson(request))
				.contentType(MediaType.APPLICATION_JSON)
		);

		// then
		resultActions.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.errorCode").value("VALIDATION_ERROR"))
			.andExpect(jsonPath("$.message").value("DTO 객체 @Valid 유효성 검사 실패"))
			.andExpect(jsonPath("$.fieldErrors.toiletLocationId").value("must not be null"));
	}

	@Test
	public void 애드온생성실패_userEmail누락오류() throws Exception {

		// given
		final String url = "/addons";
		CreateAddonRequest request = CreateAddonRequest.builder()
			.userEmail(null)  // userEmail 필수값 누락
			.toiletLocationId(1L)  // 정상값
			.memoContent("Test memo")
			.isBookmarked(false)
			.build();

		// when
		final ResultActions resultActions = mockMvc.perform(
			MockMvcRequestBuilders.post(url)
				.content(gson.toJson(request))
				.contentType(MediaType.APPLICATION_JSON)
		);

		// then
		resultActions.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.errorCode").value("VALIDATION_ERROR"))
			.andExpect(jsonPath("$.message").value("DTO 객체 @Valid 유효성 검사 실패"))
			.andExpect(jsonPath("$.fieldErrors.userEmail").value("must not be blank"));
	}

	@Test
	public void 애드온생성실패_필수값누락오류() throws Exception {

		// given
		final String url = "/addons";
		CreateAddonRequest request = CreateAddonRequest.builder()
			.userEmail(null)  // 필수값 누락
			.toiletLocationId(null)  // 필수값 누락
			.memoContent("Test memo")
			.isBookmarked(false)
			.build();

		// when
		final ResultActions resultActions = mockMvc.perform(
			MockMvcRequestBuilders.post(url)
				.content(gson.toJson(request))
				.contentType(MediaType.APPLICATION_JSON)
		);

		// then
		resultActions.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.errorCode").value("VALIDATION_ERROR"))
			.andExpect(jsonPath("$.message").value("DTO 객체 @Valid 유효성 검사 실패"))
			.andExpect(jsonPath("$.fieldErrors.userEmail").value("must not be blank"))
			.andExpect(jsonPath("$.fieldErrors.toiletLocationId").value("must not be null"));
	}

	@Test
	public void 애드온생성성공() throws Exception {

		// given
		final String url = "/addons";
		CreateAddonRequest request = CreateAddonRequest.builder()
			.userEmail("test@example.com")
			.toiletLocationId(1L)
			.memoContent("Test memo")
			.isBookmarked(false)
			.build();

		AddonCreateResponse expectedResponse = AddonCreateResponse.builder()
			.addonId(1L)
			.userEmail("test@example.com")
			.toiletLocationId(1L)
			.memoContent("Test memo")
			.isBookmarked(false)
			.build();

		when(addonService.createAddon(any(CreateAddonRequest.class))).thenReturn(expectedResponse);

		// when
		final ResultActions resultActions = mockMvc.perform(
			MockMvcRequestBuilders.post(url)
				.content(gson.toJson(request))
				.contentType(MediaType.APPLICATION_JSON)
		);

		// then
		resultActions
			.andExpect(status().isCreated())
			.andExpect(header().string("Location", "/addons/1"))
			.andExpect(jsonPath("$.addonId").value(1))
			.andExpect(jsonPath("$.userEmail").value("test@example.com"))
			.andExpect(jsonPath("$.toiletLocationId").value(1))
			.andExpect(jsonPath("$.memoContent").value("Test memo"))
			.andExpect(jsonPath("$.isBookmarked").value(false));
	}

	@Test
	public void 애드온생성실패_중복생성() throws Exception {

		// given
		final String url = "/addons";
		CreateAddonRequest request = CreateAddonRequest.builder()
			.userEmail("test@example.com")
			.toiletLocationId(1L)
			.memoContent("Test memo")
			.isBookmarked(true)
			.build();

		// 중복 예외를 던지도록 서비스 계층을 모킹
		when(addonService.createAddon(any(CreateAddonRequest.class)))
			.thenThrow(new AddonException(AddonErrorResult.DUPLICATED_ADDON_CREATE));

		// when
		final ResultActions resultActions = mockMvc.perform(
			MockMvcRequestBuilders.post(url)
				.content(gson.toJson(request))
				.contentType(MediaType.APPLICATION_JSON)
		);

		// then
		resultActions.andExpect(status().isConflict())
			.andExpect(jsonPath("$.errorCode").value("DUPLICATED_ADDON_CREATE"))
			.andExpect(jsonPath("$.message").value(AddonErrorResult.DUPLICATED_ADDON_CREATE.getMessage()));
	}
}




