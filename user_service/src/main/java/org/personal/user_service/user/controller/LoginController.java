package org.personal.user_service.user.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.personal.user_service.user.request.RequestLogin;
import org.personal.user_service.user.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String kakaoClientId;

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


    // kakao login 접근
    @GetMapping("/login/kakao")
    public ResponseEntity<String> kakaoLogin(){

        String url ="https://accounts.kakao.com/login/?continue=https%3A%2F%2Fkauth.kakao.com%2Foauth%2Fauthorize%3Fscope%3Dprofile_nickname%2520account_email%26response_type%3Dcode%26state%3D62UzJ8jpPAE34JYZCi6JszXPGW-ZnEmZwrX1lxi3voU%253D%26" +
                "redirect_uri%3Dhttp%253A%252F%252Flocalhost%253A8088%252Flogin%252Foauth2%252Fcode%252Fkakao%26through_account%3Dtrue%26" +
                "client_id%3D"+kakaoClientId +
                "#login";
        System.out.println("login return");
        return ResponseEntity.ok(url);
    }

    @RequestMapping("/auth/success")
    public void kakaoLoginRedirect(@RequestParam String accessToken, @RequestParam String refreshToken, HttpServletResponse response) throws IOException {

        response.addHeader("access", accessToken);
        response.addHeader("refresh", refreshToken);
        String redirectUrl = "http://localhost:5173/kakao-login?access=" + accessToken + "&refresh=" + refreshToken;
        response.sendRedirect(redirectUrl);
    }


}
