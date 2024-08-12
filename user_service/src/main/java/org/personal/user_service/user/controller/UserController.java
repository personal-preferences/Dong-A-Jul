package org.personal.user_service.user.controller;

import org.personal.user_service.config.DateParsing;
import org.personal.user_service.user.domain.User;
import org.personal.user_service.user.exception.InvalidRequestException;
import org.personal.user_service.user.exception.NotFoundException;
import org.personal.user_service.user.request.RequestUpdatePassword;
import org.personal.user_service.user.response.ResponseUser;
import org.personal.user_service.user.request.RequestRegist;
import org.personal.user_service.user.response.ResponseUserDetail;
import org.personal.user_service.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    //APIs
    @GetMapping("")
    public ResponseEntity<List<ResponseUser>> getUsers(){

        List<ResponseUser> userList = userService.getUserList();
        return ResponseEntity.ok(userList);
    }


    @PostMapping("/regist")
    public ResponseEntity regist(@RequestBody@Validated RequestRegist requestRegist, Errors errors){

        if (errors.hasErrors()){

            throw new InvalidRequestException(responseValidationErrors(errors));
        }

        if (!userService.registUser(requestRegist)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ResponseUser> getUser(@PathVariable Long userId){

        ResponseUser user = userService.getUser(userId);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/info")
    public ResponseEntity<ResponseUser> getMyInfo(){
        ResponseUser responseUser = userService.getMyInfo();
        return ResponseEntity.ok(responseUser);
    }

    @PutMapping("/password")
    public ResponseEntity putUserPassword(@RequestBody@Validated RequestUpdatePassword requestUpdatePassword
                                          ,Errors errors){
        if (errors.hasErrors()){
            throw new InvalidRequestException(responseValidationErrors(errors));
        }
        userService.putUserPassword(requestUpdatePassword);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity deleteUser(@PathVariable Long userId){

        userService.deleteUser(userId);
        return ResponseEntity.ok().build();
    }

    private String responseValidationErrors(Errors errors) {
        StringBuilder returnValue= new StringBuilder();
        for (int i = 0; i < errors.getAllErrors().size(); i++) {
            returnValue.append(errors.getAllErrors().get(i).getDefaultMessage()).append("\n");
        }
        return returnValue.toString();
    }
}
