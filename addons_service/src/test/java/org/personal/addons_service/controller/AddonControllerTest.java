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
import org.personal.addons_service.request.CreateAddonRequest;
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
			.build();
	}

	@Test
	public void 애드온생성실패_필수값누락() throws Exception {

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
		resultActions.andExpect(status().isBadRequest());
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

		// when
		final ResultActions resultActions = mockMvc.perform(
			MockMvcRequestBuilders.post(url)
				.content(gson.toJson(request))
				.contentType(MediaType.APPLICATION_JSON)
		);

		// then
		resultActions.andExpect(status().isCreated());
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
		doThrow(new AddonException(AddonErrorResult.DUPLICATED_ADDON_CREATE))
			.when(addonService).createAddon(any(CreateAddonRequest.class));

		// when
		final ResultActions resultActions = mockMvc.perform(
			MockMvcRequestBuilders.post(url)
				.content(gson.toJson(request))
				.contentType(MediaType.APPLICATION_JSON)
		);

		// then
		resultActions.andExpect(status().isConflict());
	}
}




