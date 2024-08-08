package org.personal.user_service.user.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.personal.user_service.config.JWTUtil;
import org.personal.user_service.user.domain.Token;
import org.personal.user_service.user.domain.User;
import org.personal.user_service.user.etc.ROLE;
import org.personal.user_service.user.exception.NotFoundException;
import org.personal.user_service.user.repository.RefreshRepository;
import org.personal.user_service.user.request.RequestLogin;
import org.personal.user_service.user.response.ResponseUserDetail;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class LoginServiceImplTest {

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Mock
    private UserService userService;

    @Mock
    private JWTUtil jwtUtil;

    @Mock
    private RefreshRepository refreshRepository;

    @InjectMocks
    private LoginServiceImpl loginService;

    private String userEmail;
    private String userPassword;
    private String bcryptPassword;
    private User user;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        userEmail = "user1@gmail.com";
        userPassword = "12341234";
        bcryptPassword = "changed_password";
        user = User.builder()
                .userId(1L)
                .userEmail(userEmail)
                .userNickname("nickname")
                .userPassword(userPassword)
                .userEnrollDate(LocalDateTime.now())
                .userDeleteDate(null)
                .userIsDeleted(false)
                .userRole(ROLE.ROLE_USER)
                .build();
    }

    @Test
    @DisplayName("로그인 성공")
    public void testLoginSuccess() {
        // given
        RequestLogin requestLogin = new RequestLogin(userEmail, userPassword);
        ResponseUserDetail responseUserDetail = new ResponseUserDetail(user);
        when(userService.getUserByEmail(anyString()))
                .thenReturn(responseUserDetail);
        when(bCryptPasswordEncoder.matches(userPassword, responseUserDetail.userPassword()))
                .thenReturn(true);
        when(jwtUtil.createJwt(anyString(), anyString(), anyString(), anyString(), anyLong()))
                .thenReturn("mockAccessToken").thenReturn("mockRefreshToken");
        HttpServletResponse response = mock(HttpServletResponse.class);

        // when
        HttpServletResponse resultResponse = loginService.login(requestLogin, response);

        // then
        // refresh, accesstoken 두개 생성
        verify(jwtUtil,times(2)).createJwt(anyString(),anyString(),anyString(),anyString(),anyLong());
        // accessToken 헤더에 담기
        verify(response).addHeader("access", "Bearer mockAccessToken");
        verify(response,times(1)).addHeader(anyString(),anyString());
        // RefreshToken 쿠키에 담기
        verify(response).addCookie(any(Cookie.class));
        verify(response,times(1)).addCookie(any());
        // 토큰 Redis에 저장
        verify(refreshRepository).save(any(Token.class));
        verify(refreshRepository,times(1)).save(any(Token.class));
        assertNotNull(resultResponse);
    }

    @Test
    @DisplayName("로그인 실패 - 아이디 불일치")
    public void testLoginEmailFail(){
        // given
        RequestLogin requestLogin = new RequestLogin(userEmail, userPassword);
        ResponseUserDetail responseUserDetail = new ResponseUserDetail(user);
        when(userService.getUserByEmail(anyString()))
                .thenThrow(new NotFoundException("회원 정보 없음"));

        // when & then
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            loginService.login(requestLogin, mock(HttpServletResponse.class));
        });
        assertEquals("회원 정보 없음", exception.getMessage());
    }

    @Test
    @DisplayName("로그인 실패- 비밀번호 불일치")
    public void testLoginPasswordFail(){
        // given
        RequestLogin requestLogin = new RequestLogin(userEmail, userPassword);
        ResponseUserDetail responseUserDetail = new ResponseUserDetail(user);
        when(userService.getUserByEmail(anyString()))
                .thenReturn(responseUserDetail);
        when(bCryptPasswordEncoder.matches(userPassword, responseUserDetail.userPassword()))
                .thenReturn(false);
        // when & then
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            loginService.login(requestLogin, mock(HttpServletResponse.class));
        });
        assertEquals("비밀번호 불일치", exception.getMessage());
    }



}