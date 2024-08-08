package org.personal.info_service.controller;

import jakarta.validation.Valid;
import org.personal.info_service.request.RequestCreateInfo;
import org.personal.info_service.response.ToiletInfoResponse;
import org.personal.info_service.service.ToiletInfoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/info")
@RestController
public class infoController {

    private final ToiletInfoService toiletInfoService;

    public infoController(ToiletInfoService toiletInfoService) {
        this.toiletInfoService = toiletInfoService;
    }

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
    public ResponseEntity<ToiletInfoResponse> deleteInfo(@PathVariable Long locationId){

        if(locationId == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        toiletInfoService.deleteToiletinfo(locationId);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
