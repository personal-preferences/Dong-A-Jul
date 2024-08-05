package org.personal.user_service.user.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.personal.user_service.user.repository.RefreshRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

class LoginServiceImplTest {

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Mock
    private RefreshRepository refreshRepository;

    @InjectMocks
    private LoginServiceImpl loginService;

    private String userEmail;
    private String userPassword;
    private String bcryptPassword;

    @BeforeEach
    public void setUp(){
        userEmail = "user1@gmail.com";
        userPassword = "12341234";
        bcryptPassword = "changed_password";
    }

    @Test
    @DisplayName("로그인 성공")
    public void testLoginSuccess(){

    }

}