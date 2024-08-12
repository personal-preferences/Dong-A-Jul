package org.personal.registration_service.controller;

import static org.personal.registration_service.common.Constants.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.personal.registration_service.exception.GlobalExceptionHandler;
import org.personal.registration_service.request.ToiletRegistApproveRequest;
import org.personal.registration_service.service.impl.ToiletRegistApproveServiceImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

@ExtendWith(MockitoExtension.class)
class ToiletRegistApproveControllerTests {

	@InjectMocks
	private ToiletRegistApproveController target;

	@Mock
	private ToiletRegistApproveServiceImpl toiletRegistApproveService;

	private ObjectMapper objectMapper;
	private MockMvc mockMvc;
	private Gson gson;

	@BeforeEach
	public void init() {
		objectMapper = new ObjectMapper();
		gson = new Gson();
		mockMvc = MockMvcBuilders.standaloneSetup(target).setControllerAdvice(new GlobalExceptionHandler()).build();
	}

	@Test
	public void 화장실등록신청등록실패_사용자식별값이헤더에없음() throws Exception {
		// given
		final String url = "/approves";

		// when
		final ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.patch(url)
			.header(USER_ID_HEADER, "")
			.content(gson.toJson(toiletRegistRequest()))
			.contentType(MediaType.APPLICATION_JSON));

		// then
		resultActions.andExpect(status().isBadRequest());
	}

	@Test
	public void 화장실등록신청확인실패_관리자가아님() throws Exception {
		// given
		final String url = "/approves";

		// when
		final ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.patch(url)
			.header(USER_ID_HEADER, "12345")
			.content(gson.toJson(toiletRegistRequest()))
			.contentType(MediaType.APPLICATION_JSON));

		// then
		resultActions.andExpect(status().isForbidden());
	}

	@Test
	public void 화장실등록신청확인성공_관리자확인() throws Exception {
		// given
		final String url = "/approves";

		// when
		final ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.patch(url)
			.header(USER_ID_HEADER, "admin")
			.content(gson.toJson(toiletRegistRequest()))
			.contentType(MediaType.APPLICATION_JSON));

		// then
		resultActions.andExpect(status().isOk());
	}

	private ToiletRegistApproveRequest toiletRegistRequest() {
		return ToiletRegistApproveRequest.builder()
			.toiletRegistId(1L)
			.toiletRegistIsApproved(false)
			.build();
	}

}