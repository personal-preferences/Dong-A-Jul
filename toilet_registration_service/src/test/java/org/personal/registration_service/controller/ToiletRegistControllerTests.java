package org.personal.registration_service.controller;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.mockito.Mockito.*;
import static org.personal.registration_service.common.Constants.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.personal.registration_service.common.ToiletRegistErrorResult;
import org.personal.registration_service.exception.ToiletRegistException;
import org.personal.registration_service.request.ToiletRegistRequest;
import org.personal.registration_service.service.impl.ToiletRegistServiceImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.google.gson.Gson;

@ExtendWith(MockitoExtension.class)
class ToiletRegistControllerTests {

	private final Double lat = 37.123456;
	private final Double lng = 127.123456;

	@Mock
	private ToiletRegistServiceImpl toiletRegistService;

	@InjectMocks
	private ToiletRegistController target;

	private MockMvc mockMvc;
	private Gson gson;

	@BeforeEach
	public void init(){
		gson = new Gson();
		mockMvc = MockMvcBuilders.standaloneSetup(target).build();
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

	private ToiletRegistRequest toiletRegistRequest(final Double lat, final Double lng){
		return ToiletRegistRequest.builder()
			.toiletRegistLatitude(lat)
			.toiletRegistLongitude(lng)
			.build();
	}

}