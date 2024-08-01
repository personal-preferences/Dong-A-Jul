package org.personal.user_service.user.controller;

import org.personal.user_service.config.DateParsing;
import org.personal.user_service.user.domain.User;
import org.personal.user_service.user.exception.InvalidRequestException;
import org.personal.user_service.user.exception.NotFoundException;
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

    @GetMapping("/{userId}")
    public ResponseEntity<ResponseUser> getUser(@PathVariable int userId){
        User user = userService.getUser(userId);
        ResponseUser responseUser = new ResponseUser(
                user.getUserEmail(),
                user.getUserNickname(),
                DateParsing.LdtToStr(user.getUserEnrollDate()),
                DateParsing.LdtToStr(user.getUserDeleteDate()),
                user.isUserIsDeleted(),
                user.getUserRole()
        );
        return ResponseEntity.ok(responseUser);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(NotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<String> handleInvalidRequestException(InvalidRequestException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
