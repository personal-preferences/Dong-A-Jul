package org.personal.info_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.personal.info_service.request.RequestCreateInfo;
import org.personal.info_service.service.ToiletInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.not;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class infoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ToiletInfoService toiletInfoService;

    @Test
    @DisplayName("화장실 정보 등록 성공")
    void addToiletInfoSuccess() throws Exception {

        RequestCreateInfo request = createTestToiletInfo();
        String json = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/info/add")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    @DisplayName("정보 추가 실패 - locationId 없는 경우")
    void addInfoNoLocationId() throws Exception {

        RequestCreateInfo request = RequestCreateInfo.builder().toiletLocationId(null).build();
        String json = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/info/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("화장실 정보 수정 성공")
    void modifyToiletInfoSuccess() throws Exception {

        RequestCreateInfo origin = RequestCreateInfo.builder()
                .toiletLocationId(1L)
                .toiletInfoFemaleToiletsNumber(3)
                .build();
        toiletInfoService.createToiletInfo(origin);

        RequestCreateInfo request = RequestCreateInfo.builder()
                .toiletLocationId(1L)
                .toiletInfoFemaleToiletsNumber(5)
                .build();
        String json = objectMapper.writeValueAsString(request);

        mockMvc.perform(patch("/info/modify")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.toiletInfoFemaleToiletsNumber").value(5))
                .andDo(print());
    }

    @Test
    @DisplayName("정보 수정 실패 - 저장된 값이 없는 경우")
    void modifyInfoIllegalArgumentException() throws Exception {

        RequestCreateInfo request = RequestCreateInfo.builder()
                .toiletLocationId(-1L)
                .toiletInfoFemaleToiletsNumber(5)
                .build();
        String json = objectMapper.writeValueAsString(request);

        mockMvc.perform(patch("/info/modify")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    @DisplayName("화장실 정보 삭제 성공")
    void deleteToiletInfoSuccess() throws Exception {

        RequestCreateInfo origin = RequestCreateInfo.builder()
                .toiletLocationId(1L)
                .build();
        toiletInfoService.createToiletInfo(origin);

        mockMvc.perform(delete("/info/delete/" + origin.toiletLocationId())
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("정보 삭제 실패 - 저장된 값이 없는 경우")
    void deleteInfoIllegalArgumentException() throws Exception {

        mockMvc.perform(delete("/info/delete/" + -1L)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    @DisplayName("화장실 정보 조회 성공")
    void getToiletInfoSuccess() throws Exception {

        RequestCreateInfo origin = RequestCreateInfo.builder()
                .toiletLocationId(1L)
                .build();
        toiletInfoService.createToiletInfo(origin);

        mockMvc.perform(get("/info/" + origin.toiletLocationId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(not(emptyOrNullString())))
                .andDo(print());
    }

    @Test
    @DisplayName("화장실 정보 리스트 조회 성공")
    void getToiletInfoListSuccess() throws Exception {

        RequestCreateInfo origin1 = RequestCreateInfo.builder()
                .toiletLocationId(1L)
                .build();
        RequestCreateInfo origin2 = RequestCreateInfo.builder()
                .toiletLocationId(2L)
                .build();
        toiletInfoService.createToiletInfo(origin1);
        toiletInfoService.createToiletInfo(origin2);

        List<Long> idList = Arrays.asList(origin1.toiletLocationId(), origin2.toiletLocationId());
        String json = objectMapper.writeValueAsString(idList);

        mockMvc.perform(post("/info")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(content().string(not(emptyOrNullString())))
                .andDo(print());
    }

    public RequestCreateInfo createTestToiletInfo() {
        return RequestCreateInfo.builder()
                .isDeleted(false)
                .toiletInfoManagementAgency("Public Agency")
                .toiletInfoPhoneNumber("123-456-7890")
                .toiletInfoOpeningHours("09:00 - 18:00")
                .toiletInfoOpeningHoursDetails("Open all week except holidays")
                .toiletInfoInstallationYearMonth("2024-07")
                .toiletInfoOwnershipType("Public")
                .toiletInfoWasteDisposalMethod("Sewage")
                .toiletInfoSafetyFacilityInstallationIsRequired(true)
                .toiletInfoEmergencyBellIsInstalled(true)
                .toiletInfoEmergencyBellLocation("Near entrance")
                .toiletInfoEntranceCCTVIsInstalled(true)
                .toiletInfoDiaperChangingTableIsAvailable(true)
                .toiletInfoDiaperChangingTableLocation("Corner near entrance")
                .toiletInfoMaleToiletsNumber(3)
                .toiletInfoMaleUrinalsNumber(4)
                .toiletInfoMaleDisabledToiletsNumber(1)
                .toiletInfoMaleDisabledUrinalsNumber(1)
                .toiletInfoMaleChildToiletsNumber(1)
                .toiletInfoMaleChildUrinalsNumber(1)
                .toiletInfoFemaleToiletsNumber(4)
                .toiletInfoFemaleDisabledToiletsNumber(1)
                .toiletInfoFemaleChildToiletsNumber(1)
                .toiletLocationId(1L)
                .build();
    }
}