package org.personal.user_service.user.controller;

import org.personal.user_service.user.response.ResponseUser;
import org.personal.user_service.user.service.RequestRegist;
import org.personal.user_service.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public ResponseEntity<List<ResponseUser>> getUsers(){
        List<ResponseUser> userDTOList = userService.getUserList();
        return ResponseEntity.ok(userDTOList);
    }
    @PostMapping("/regist")
    public ResponseEntity regist(@RequestBody RequestRegist requestRegist){
        if (!userService.registUser(requestRegist)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok().build();
    }

}
