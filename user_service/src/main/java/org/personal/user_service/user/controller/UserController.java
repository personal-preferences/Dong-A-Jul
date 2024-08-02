package org.personal.user_service.user.controller;

import org.personal.user_service.config.DateParsing;
import org.personal.user_service.user.domain.User;
import org.personal.user_service.user.exception.InvalidRequestException;
import org.personal.user_service.user.exception.NotFoundException;
import org.personal.user_service.user.request.RequestUpdatePassword;
import org.personal.user_service.user.response.ResponseUser;
import org.personal.user_service.user.request.RequestRegist;
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

        List<User> userList = userService.getUserList();
        List<ResponseUser> responseUserList = userList.stream().map(this::convertToResponseUser).toList();
        return ResponseEntity.ok(responseUserList);
    }


    @PostMapping("/regist")
    public ResponseEntity regist(@RequestBody@Validated RequestRegist requestRegist, Errors errors){

        if (errors.hasErrors()){

            throw new InvalidRequestException(checkValidation(errors));
        }

        if (!userService.registUser(requestRegist)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ResponseUser> getUser(@PathVariable Long userId){

        User user = userService.getUser(userId);
        ResponseUser responseUser = convertToResponseUser(user);
        return ResponseEntity.ok(responseUser);
    }

    @PutMapping("/password")
    public ResponseEntity putUserPassword(@RequestBody@Validated RequestUpdatePassword requestUpdatePassword
                                          ,Errors errors){
        if (errors.hasErrors()){
            throw new InvalidRequestException(checkValidation(errors));
        }
        userService.putUserPassword(requestUpdatePassword);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity deleteUser(@PathVariable Long userId){

        userService.deleteUser(userId);
        return ResponseEntity.ok().build();
    }


    // User to ResponseUser
    private ResponseUser convertToResponseUser(User user) {

        return new ResponseUser(user);
    }

    private String checkValidation(Errors errors) {
        StringBuilder returnValue= new StringBuilder();
        for (int i = 0; i < errors.getAllErrors().size(); i++) {
            returnValue.append(errors.getAllErrors().get(i).getDefaultMessage()).append("\n");
        }
        return returnValue.toString();
    }
}
