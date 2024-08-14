package org.personal.user_service.user.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.personal.user_service.config.JWTUtil;
import org.personal.user_service.config.TOKENTIME;
import org.personal.user_service.user.domain.Token;
import org.personal.user_service.user.exception.InvalidRequestException;
import org.personal.user_service.user.exception.NotFoundException;
import org.personal.user_service.user.repository.RefreshRepository;
import org.personal.user_service.user.request.RequestLogin;
import org.personal.user_service.user.request.RequestRegist;
import org.personal.user_service.user.response.ResponseToken;
import org.personal.user_service.user.response.ResponseUserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

@Service
public class LoginServiceImpl implements LoginService{

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final JWTUtil jwtUtil;
    private final RefreshRepository refreshRepository;

    @Autowired
    public LoginServiceImpl(PasswordEncoder passwordEncoder, UserService userService, JWTUtil jwtUtil, RefreshRepository refreshRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.refreshRepository = refreshRepository;
    }


    @Override
    @Transactional(readOnly = false)
    public HttpServletResponse login(RequestLogin requestLogin, HttpServletResponse response) {

        ResponseUserDetail responseUser = userService.getUserByEmail(requestLogin.userEmail());
        if(!passwordEncoder.matches(requestLogin.userPassword(),responseUser.userPassword())){
            throw new NotFoundException("비밀번호 불일치");
        }

        String accessToken = jwtUtil.createJwt("access", responseUser.userEmail(), responseUser.userNickname(),
                responseUser.userRole().name(), TOKENTIME.ACCESS.label()); // 1시간
        String refreshToken = jwtUtil.createJwt("refresh", responseUser.userEmail(), responseUser.userNickname(),
                responseUser.userRole().name(),TOKENTIME.REFRESH.label() ); // 24시간
        response.addHeader("access", "Bearer " + accessToken);
        response.addCookie(createCookie("refresh",refreshToken));

        Token token = new Token(responseUser.userNickname(),refreshToken,86400 ); //24시간
        refreshRepository.save(token);
        return response;
    }

    @Override
    @Transactional(readOnly = false)
    public void logout(HttpServletRequest req) {
        String refresh = null;

        try {
            // refresh가 쿠키에 담겨있는지 확인하고 담는 로직
            refresh = findRefresh(req);
        }catch (NullPointerException e){
            throwRefreshIsNull();
        }

        Optional<Token> tokenOpt = refreshRepository.findByToken(refresh);

        try {
            tokenOpt.ifPresent(refreshRepository::delete);
        }catch (Exception e){
            throw new InvalidRequestException("refresh 토큰 유효하지 않음");
        }
    }

    private String findRefresh(HttpServletRequest req) {

        String refresh = null;
        Cookie[] cookies = req.getCookies();
        for(Cookie cookie : cookies){
            if(cookie.getName().equals("refresh")){
                refresh = cookie.getValue();
                break;
            }
        }

        if (refresh==null)
            throwRefreshIsNull();

        return  refresh;
    }

    private static void throwRefreshIsNull() {

        throw new NotFoundException("refresh 없음");
    }

    @Override
    public ResponseToken processOAuth2User(OAuth2User oAuth2User) {

        // 사용자 정보 매핑
        Map<String, Object> attributes = oAuth2User.getAttributes();

        // Kakao의 사용자 정보 API에서 반환된 정보가 중첩된 구조일 경우 이를 풀어헤칩니다.
        Map<String, Object> account = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) account.get("profile");

        String nickname = (String) profile.get("nickname");
        String email = (String) account.get("email");
        String role = "ROLE_USER"; // default: user

        // 카카오 회원 초기 비밀번호는 12341234입니다.
        RequestRegist requestRegistUser = new RequestRegist(email,nickname,"12341234","ROLE_USER");

        userService.registKakaoUser(requestRegistUser);

        // JWT 토큰을 생성 후 전달
        return createTokens(email, nickname, role);
    }

    private ResponseToken createTokens(String email, String nickname, String role) {

        String accessToken = jwtUtil.createJwt("access", email, nickname, role, TOKENTIME.ACCESS.label());
        String refreshToken = jwtUtil.createJwt("refresh", email, nickname, role, TOKENTIME.REFRESH.label());
        return new ResponseToken(accessToken, refreshToken);
    }

    private Cookie createCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(24*60*60); // 초
//        cookie.setSecure(true);   //https 인 경우 사용
        cookie.setPath("/");   //쿠키 지정 범위
        cookie.setHttpOnly(true);

        return cookie;
    }


}
