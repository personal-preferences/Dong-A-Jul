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

        try {
            ToiletInfoResponse savedToilet = toiletInfoService.createToiletInfo(toiletInfo);
            return ResponseEntity.status(HttpStatus.OK).body(savedToilet);
        } catch (Exception e) {
            System.err.println("addInfo: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PostMapping("/modify")
    public ResponseEntity<ToiletInfoResponse> modifyInfo(@RequestBody RequestCreateInfo toiletInfo){

        if(toiletInfo.toiletLocationId() == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        try {
            ToiletInfoResponse updatedToilet = toiletInfoService.updateToiletInfo(toiletInfo);
            return ResponseEntity.status(HttpStatus.OK).body(updatedToilet);
        } catch (Exception e) {
            System.err.println("modifyInfo: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PostMapping("/{locationId}")
    public ResponseEntity<ToiletInfoResponse> deleteInfo(@PathVariable Long locationId){

        if(locationId == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        try {
            toiletInfoService.deleteToiletinfo(locationId);
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } catch (Exception e) {
            System.err.println("deleteInfo: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
