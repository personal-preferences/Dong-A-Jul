package org.personal.info_service.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.personal.info_service.messaging.MessageProducer;
import org.personal.info_service.request.RequestCreateInfo;
import org.personal.info_service.response.ToiletInfoResponse;
import org.personal.info_service.service.ToiletInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping("/info")
@RestController
@RequiredArgsConstructor
public class infoController {

    private final ToiletInfoService toiletInfoService;
    private final MessageProducer messageProducer;

    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping("/add")
    public ResponseEntity<ToiletInfoResponse> addInfo(@RequestBody RequestCreateInfo toiletInfo){

        if(toiletInfo.toiletLocationId() == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        ToiletInfoResponse savedToilet = toiletInfoService.createToiletInfo(toiletInfo);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedToilet);

    }

    @PatchMapping("/modify")
    public ResponseEntity<ToiletInfoResponse> modifyInfo(@RequestBody RequestCreateInfo toiletInfo){
        if(toiletInfo.toiletLocationId() == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        ToiletInfoResponse updatedToilet = toiletInfoService.updateToiletInfo(toiletInfo);
        return ResponseEntity.status(HttpStatus.OK).body(updatedToilet);
    }

    @DeleteMapping("/delete/{locationId}")
    public ResponseEntity<Void> deleteInfo(@PathVariable Long locationId){

        if(locationId == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        toiletInfoService.deleteToiletinfo(locationId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/{locationId}")
    public ResponseEntity<ToiletInfoResponse> getToiletInfo(@PathVariable Long locationId){

        if(locationId == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        ToiletInfoResponse info = toiletInfoService.getToiletInfo(locationId);

        return ResponseEntity.status(HttpStatus.OK).body(info);
    }

    @PostMapping("")
    public ResponseEntity<List<ToiletInfoResponse>> getToiletInfoList(@RequestBody List<Long> idList){

        if(idList.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        List<ToiletInfoResponse> infoList = toiletInfoService.getToiletInfoList(idList);

        return ResponseEntity.status(HttpStatus.OK).body(infoList);
    }

    @GetMapping("/disabledToilet")
    public ResponseEntity<List<ToiletInfoResponse>> getDisabledToiletInfoList(){

        List<ToiletInfoResponse> infoList = toiletInfoService.getDisabledToiletList();

        return ResponseEntity.status(HttpStatus.OK).body(infoList);
    }

    // 카프카 테스트
    @GetMapping("/kafka/regist")
    public String createInfoTest() throws JsonProcessingException {

        String json = objectMapper.writeValueAsString(createTestToiletInfo());
        messageProducer.send("test", json);

        return "regist ToiletInfo";
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
