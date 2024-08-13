package org.personal.user_service.user.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("")
@RequiredArgsConstructor
public class LoginController {

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String kakaoClientId;
    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String kakaoRedirect;
    private final LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody@Validated RequestLogin requestLogin,
                                        HttpServletResponse response, Errors errors){

        response = loginService.login(requestLogin, response);

        return ResponseEntity.ok().build();
    }



    @DeleteMapping("/login")
    public ResponseEntity<String> logout(HttpServletRequest req){
        loginService.logout(req);
        return ResponseEntity.ok().build();
    }


    // kakao login 접근
    @GetMapping("/login/kakao")
    public ResponseEntity<String> kakaoLogin(){

        String url ="https://kauth.kakao.com/oauth/authorize?" +
                "client_id=" +
                kakaoClientId +
                "&" +
                "redirect_uri=" +
                kakaoRedirect +
                "&response_type=code";
        System.out.println("login return");
        return ResponseEntity.ok(url);
    }

    @RequestMapping("/auth/success")
    public void kakaoLoginRedirect(@RequestParam String accessToken, @RequestParam String refreshToken, HttpServletResponse response) throws IOException {

        response.addHeader("access", accessToken);
        response.addHeader("refresh", refreshToken);
        // 아래는 프론트 화면 구현되면 리다이렉트 시킬 url로 지정할 것
//        String redirectUrl = "http://localhost:8765/kakao/login?access=" + accessToken + "&refresh=" + refreshToken;
//        response.sendRedirect(redirectUrl);
        System.out.println("카카오 로그인 완료");
    }


}
