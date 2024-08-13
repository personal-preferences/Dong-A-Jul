package org.personal.registration_service.controller;

import static org.mockito.Mockito.*;
import static org.personal.registration_service.common.Constants.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.personal.registration_service.common.ToiletRegistErrorResult;
import org.personal.registration_service.exception.GlobalExceptionHandler;
import org.personal.registration_service.exception.ToiletRegistException;
import org.personal.registration_service.request.ToiletRegistApproveRequest;
import org.personal.registration_service.response.ToiletRegistResponse;
import org.personal.registration_service.service.impl.ToiletRegistApproveServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

	@Test
	public void 특정화장실등록조회_성공() throws Exception {
		// given
		final String url = "/approves/1";

		ToiletRegistResponse response = ToiletRegistResponse.builder()
			.toiletRegistId(1L)
			.toiletRegistDate("2023-01-01")
			.toiletRegistIsApproved(true)
			.toiletRegistConfirmedDate("2023-01-02")
			.toiletRegistToiletName("예시 화장실")
			.toiletRegistRoadNameAddress("서울특별시")
			.toiletRegistNumberAddress("123-456")
			.toiletRegistLatitude(37.5665)
			.toiletRegistLongitude(126.9780)
			.toiletRegistManagementAgency("관리기관")
			.toiletRegistPhoneNumber("010-1234-5678")
			.toiletRegistOpeningHours("09:00-18:00")
			.toiletRegistOpeningHoursDetails("평일만 운영")
			.toiletRegistInstallationYearMonth("2020-01")
			.toiletRegistOwnershipType("공공")
			.toiletRegistWasteDisposalMethod("하수")
			.toiletInfoSafetyFacilityInstallationIsRequired(true)
			.toiletRegistEmergencyBellIsInstalled(true)
			.toiletRegistEmergencyBellLocation("입구 근처")
			.toiletRegistEntranceCctvIsInstalled(true)
			.toiletRegistDiaperChangingTableIsAvailable(true)
			.toiletRegistDiaperChangingTableLocation("메인 홀 근처")
			.toiletRegistMaleToiletsNumber(3)
			.toiletRegistMaleUrinalsNumber(2)
			.toiletRegistMaleDisabledToiletsNumber(1)
			.toiletRegistMaleDisabledUrinalsNumber(1)
			.toiletRegistMaleChildToiletsNumber(1)
			.toiletRegistMaleChildUrinalsNumber(1)
			.toiletRegistFemaleToiletsNumber(3)
			.toiletRegistFemaleDisabledToiletsNumber(1)
			.toiletRegistFemaleChildToiletsNumber(1)
			.userEmail("user@example.com")
			.build();

		when(toiletRegistApproveService.getToiletRegist(1L)).thenReturn(response);

		// when
		final ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get(url)
			.param("toiletRegistId", "1")
			.contentType(MediaType.APPLICATION_JSON));

		// then
		resultActions.andExpect(status().isOk())
			.andExpect(jsonPath("$.toiletRegistToiletName").value("예시 화장실"));
	}

	@Test
	public void 특정화장실등록조회실패_존재하지않음() throws Exception {
		// given
		final String url = "/approves/1";
		when(toiletRegistApproveService.getToiletRegist(1L))
			.thenThrow(new ToiletRegistException(ToiletRegistErrorResult.ENTITY_NOT_FOUND));

		// when
		final ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get(url)
			.param("toiletRegistId", "1")
			.contentType(MediaType.APPLICATION_JSON));

		// then
		resultActions.andExpect(status().isNotFound());
	}

	@Test
	void 화장실등록신청전체조회성공() throws Exception {
		// given
		final String url = "/approves";
		ToiletRegistResponse response1 = ToiletRegistResponse.builder()
			.toiletRegistId(1L)
			.toiletRegistToiletName("화장실 1")
			.build();

		ToiletRegistResponse response2 = ToiletRegistResponse.builder()
			.toiletRegistId(2L)
			.toiletRegistToiletName("화장실 2")
			.build();

		Pageable pageable = PageRequest.of(0, 10);
		Page<ToiletRegistResponse> page = new PageImpl<>(Arrays.asList(response1, response2), pageable, 2);

		when(toiletRegistApproveService.listToiletRegist(0)).thenReturn(page);

		// when & then
		mockMvc.perform(get(url) // 실제 엔드포인트로 변경하세요
				.param("pageNum", "0")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.content[0].toiletRegistId").value(1L))
			.andExpect(jsonPath("$.content[0].toiletRegistToiletName").value("화장실 1"))
			.andExpect(jsonPath("$.content[1].toiletRegistId").value(2L))
			.andExpect(jsonPath("$.content[1].toiletRegistToiletName").value("화장실 2"));
	}

	private ToiletRegistApproveRequest toiletRegistRequest() {
		return ToiletRegistApproveRequest.builder()
			.toiletRegistId(1L)
			.isApproved(false)
			.build();
	}

}