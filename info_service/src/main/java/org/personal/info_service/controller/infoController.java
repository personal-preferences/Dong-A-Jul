package org.personal.info_service.controller;

import org.personal.info_service.response.ToiletInfoResponse;
import org.personal.info_service.service.ToiletInfoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/info")
public class infoController {

    private final ToiletInfoService toiletInfoService;

    public infoController(ToiletInfoService toiletInfoService) {
        this.toiletInfoService = toiletInfoService;
    }

    @PostMapping("/add")
    public ResponseEntity<ToiletInfoResponse> addInfo(@RequestBody ToiletInfoResponse toiletInfo){
        try {
            ToiletInfoResponse savedToilet = toiletInfoService.createToiletInfo(toiletInfo);
            return ResponseEntity.status(HttpStatus.OK).body(savedToilet);
        } catch (Exception e) {
            System.err.println("addInfo: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
