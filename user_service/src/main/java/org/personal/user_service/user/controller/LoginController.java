package org.personal.user_service.user.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.personal.user_service.user.request.RequestLogin;
import org.personal.user_service.user.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class LoginController {

    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }


    @PostMapping("")
    public ResponseEntity<String> login(@RequestBody@Validated RequestLogin requestLogin,
                                        HttpServletResponse response, Errors errors){

        response = loginService.login(requestLogin, response);

        return ResponseEntity.ok().build();
    }
    @DeleteMapping("")
    public ResponseEntity<String> logout(HttpServletRequest req){
        loginService.logout(req);
        return ResponseEntity.ok().build();
    }
}
