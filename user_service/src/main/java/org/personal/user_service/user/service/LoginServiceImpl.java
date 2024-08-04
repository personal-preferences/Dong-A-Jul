package org.personal.user_service.user.service;

import jakarta.servlet.http.HttpServletResponse;
import org.personal.user_service.config.JWTUtil;
import org.personal.user_service.user.exception.NotFoundException;
import org.personal.user_service.user.request.RequestLogin;
import org.personal.user_service.user.response.ResponseUserDetail;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService{

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final JWTUtil jwtUtil;

    public LoginServiceImpl(PasswordEncoder passwordEncoder, UserService userService, JWTUtil jwtUtil) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }


    @Override
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
        response.addHeader("refresh", "Bearer " + refreshToken);

        return response;
    }
}
