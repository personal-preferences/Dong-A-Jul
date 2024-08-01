package org.personal.user_service.user.controller;

import org.personal.user_service.user.response.ResponseUser;
import org.personal.user_service.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
