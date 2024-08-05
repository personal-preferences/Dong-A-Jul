package org.personal.user_service.user.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.personal.user_service.config.GetUserInfo;
import org.personal.user_service.config.JWTUtil;
import org.personal.user_service.user.domain.Token;
import org.personal.user_service.user.exception.NotFoundException;
import org.personal.user_service.user.repository.RefreshRepository;
import org.personal.user_service.user.request.RequestLogin;
import org.personal.user_service.user.response.ResponseUserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class LoginServiceImpl implements LoginService{

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final JWTUtil jwtUtil;
    private final RefreshRepository refreshRepository;
    private final GetUserInfo getUserInfo;

    @Autowired
    public LoginServiceImpl(PasswordEncoder passwordEncoder, UserService userService, JWTUtil jwtUtil, RefreshRepository refreshRepository, GetUserInfo getUserInfo) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.refreshRepository = refreshRepository;
        this.getUserInfo = getUserInfo;
    }


    @Override
    @Transactional(readOnly = false)
    public HttpServletResponse login(RequestLogin requestLogin, HttpServletResponse response) {

        ResponseUserDetail responseUser = userService.getUserByEmail(requestLogin.userEmail());
        if(!passwordEncoder.matches(requestLogin.userPassword(),responseUser.userPassword())){
            throw new NotFoundException("비밀번호 불일치");
        }

        String accessToken = jwtUtil.createJwt("access", responseUser.userEmail(), responseUser.userNickname(),
                responseUser.userRole().name(),860000L );
        String refreshToken = jwtUtil.createJwt("refresh", responseUser.userEmail(), responseUser.userNickname(),
                responseUser.userRole().name(),860000L );
        response.addHeader("access", "Bearer " + accessToken);
        response.addCookie(createCookie("refresh",refreshToken));

        Token token = new Token(responseUser.userNickname(),refreshToken,20 ); //임시 20초
        refreshRepository.save(token);
        return response;
    }

    @Override
    @Transactional(readOnly = false)
    public void logout(HttpServletRequest req) {
        String refresh = null;
        Cookie[] cookies = req.getCookies();
        for(Cookie cookie : cookies){
            if(cookie.getName().equals("refresh")){
                refresh = cookie.getValue();
            }
        }
        System.out.println(getUserInfo.getName());
        System.out.println("refresh = " + refresh);
        if (refresh==null)
            throw new NotFoundException("refresh 없음");

        Optional<Token> tokenOpt = refreshRepository.findByToken(refresh);
        tokenOpt.ifPresent(refreshRepository::delete);
    }

    private Cookie createCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(24*60*60);
//        cookie.setSecure(true);   //https 인 경우 사용
        cookie.setPath("/");   //쿠키 지정 범위
        cookie.setHttpOnly(true);

        return cookie;
    }
}
