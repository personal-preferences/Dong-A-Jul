package org.personal.registration_service.controller;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.mockito.Mockito.*;
import static org.personal.registration_service.common.Constants.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.personal.registration_service.common.DateParsing;
import org.personal.registration_service.common.ToiletRegistErrorResult;
import org.personal.registration_service.exception.GlobalExceptionHandler;
import org.personal.registration_service.exception.ToiletRegistException;
import org.personal.registration_service.request.ToiletRegistRequest;
import org.personal.registration_service.response.ToiletRegistResponse;
import org.personal.registration_service.service.impl.ToiletRegistServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

@ExtendWith(MockitoExtension.class)
class ToiletRegistControllerTests {

	private final Double lat = 37.123456;
	private final Double lng = 127.123456;

	@Mock
	private ToiletRegistServiceImpl toiletRegistService;

	@InjectMocks
	private ToiletRegistController target;

	private ObjectMapper objectMapper;
	private MockMvc mockMvc;
	private Gson gson;

	@BeforeEach
	public void init(){
		objectMapper = new ObjectMapper();
		gson = new Gson();
		mockMvc = MockMvcBuilders.standaloneSetup(target)
			.setControllerAdvice(new GlobalExceptionHandler())
			.build();
	}

	@Test
	public void mockMvc가Null이아님() throws Exception {
		assertThat(target).isNotNull();
		assertThat(mockMvc).isNotNull();
	}

	@Test
	public void 화장실등록신청등록실패_사용자식별값이헤더에없음() throws Exception {
		// given
		final String url = "/toiletregists";

		// when
		final ResultActions resultActions = mockMvc.perform(
			MockMvcRequestBuilders.post(url)
				.header(USER_ID_HEADER, "")
				.content(gson.toJson(toiletRegistRequest(lat, lng)))
				.contentType(MediaType.APPLICATION_JSON)
		);

		// then
		resultActions.andExpect(status().isBadRequest());

	}

	@Test
	public void 화장실등록신청등록실패_위도경도가null() throws Exception {
		// given
		final String url = "/toiletregists";

		// when
		final ResultActions resultActions = mockMvc.perform(
			MockMvcRequestBuilders.post(url)
				.header(USER_ID_HEADER, "12345")
				.content(gson.toJson(toiletRegistRequest(null, null)))
				.contentType(MediaType.APPLICATION_JSON)
		);

		// then
		resultActions.andExpect(status().isBadRequest());
	}

	@Test
	public void 화장실등록신청등록실패_MemberService에서에러Throw() throws Exception {
		// given
		final String url = "/toiletregists";
		doThrow(new ToiletRegistException(ToiletRegistErrorResult.DUPLICATED_TOILET_REGIST_REGISTER))
			.when(toiletRegistService)
			.addToiletRegist(lat, lng);

		// when
		final ResultActions resultActions = mockMvc.perform(
			MockMvcRequestBuilders.post(url)
				.header(USER_ID_HEADER, "12345")
				.content(gson.toJson(toiletRegistRequest(lat, lng)))
				.contentType(MediaType.APPLICATION_JSON)
		);

		// then
		resultActions.andExpect(status().isBadRequest());

	}

	@Test
	public void 화장실등록신청등록성공() throws Exception {
		// given
		final String url = "/toiletregists";
		final ToiletRegistResponse toiletRegistResponse = ToiletRegistResponse.builder()
			.toiletRegistId(1L)
			.toiletRegistIsApproved(false)
			.toiletRegistDate(DateParsing.LdtToStr(LocalDateTime.now()))
			.toiletRegistLatitude(lat)
			.toiletRegistLongitude(lng)
			.build();

		doReturn(toiletRegistResponse).when(toiletRegistService).addToiletRegist(lat, lng);

		// when
		final ResultActions resultActions = mockMvc.perform(
			MockMvcRequestBuilders.post(url)
				.header(USER_ID_HEADER, "12345")
				.content(objectMapper.writeValueAsString(toiletRegistRequest(lat, lng)))  // JSON 변환
				.contentType(MediaType.APPLICATION_JSON)
		);

		// then
		resultActions.andExpect(status().isCreated());

		final ToiletRegistResponse response = objectMapper.readValue(
			resultActions.andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8),
			ToiletRegistResponse.class
		);

		assertThat(response).isNotNull();
		assertThat(response.toiletRegistId()).isEqualTo(1L);
		assertThat(response.toiletRegistIsApproved()).isEqualTo(false);
		assertThat(response.toiletRegistLatitude()).isEqualTo(lat);
		assertThat(response.toiletRegistLongitude()).isEqualTo(lng);
	}

	private ToiletRegistRequest toiletRegistRequest(final Double lat, final Double lng){
		return ToiletRegistRequest.builder()
			.toiletRegistLatitude(lat)
			.toiletRegistLongitude(lng)
			.build();
	}
}