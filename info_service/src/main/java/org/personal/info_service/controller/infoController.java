package org.personal.info_service.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.personal.info_service.request.RequestCreateInfo;
import org.personal.info_service.response.ToiletInfoResponse;
import org.personal.info_service.service.ToiletInfoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping("/info")
@RestController
@RequiredArgsConstructor
public class infoController {

    private final ToiletInfoService toiletInfoService;

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

}
