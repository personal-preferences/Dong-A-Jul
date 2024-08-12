package org.personal.registration_service.controller;

import static org.personal.registration_service.common.Constants.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.personal.registration_service.common.DateParsing;
import org.personal.registration_service.exception.GlobalExceptionHandler;
import org.personal.registration_service.request.ToiletRegistApproveRequest;
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

	private final Double lat = 37.123456;
	private final Double lng = 127.123456;

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
			.content(gson.toJson(toiletRegistRequest(lat, lng)))
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
			.content(gson.toJson(toiletRegistRequest(lat, lng)))
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
			.content(gson.toJson(toiletRegistRequest(lat, lng)))
			.contentType(MediaType.APPLICATION_JSON));

		// then
		resultActions.andExpect(status().isOk());
	}

	@Test
	public void 화장실등록신청실패_이미승인된신청() throws Exception {
		// given
		final String url = "/approves";

		// when
		final ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.patch(url)
			.header(USER_ID_HEADER, "admin")
			.content(gson.toJson(toiletRegistApprovedRequest(lat, lng)))
			.contentType(MediaType.APPLICATION_JSON));

		// then
		resultActions.andExpect(status().isBadRequest());
	}

	private ToiletRegistApproveRequest toiletRegistRequest(final Double lat, final Double lng) {
		return ToiletRegistApproveRequest.builder()
			.toiletRegistId(1L)
			.toiletRegistIsApproved(false)
			.toiletRegistDate(DateParsing.LdtToStr(LocalDateTime.now()))
			.toiletRegistConfirmedDate(null)
			.toiletRegistLatitude(lat)
			.toiletRegistLongitude(lng)
			.build();
	}

	private ToiletRegistApproveRequest toiletRegistApprovedRequest(final Double lat, final Double lng) {
		return ToiletRegistApproveRequest.builder()
			.toiletRegistId(1L)
			.toiletRegistIsApproved(true)
			.toiletRegistDate(DateParsing.LdtToStr(LocalDateTime.now()))
			.toiletRegistConfirmedDate(DateParsing.LdtToStr(LocalDateTime.now()))
			.toiletRegistLatitude(lat)
			.toiletRegistLongitude(lng)
			.build();
	}

}