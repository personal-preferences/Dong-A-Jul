package org.personal.user_service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.personal.user_service.config.DateParsing;
import org.personal.user_service.user.domain.User;
import org.personal.user_service.user.etc.ROLE;
import org.personal.user_service.user.exception.InvalidRequestException;
import org.personal.user_service.user.repository.UserRepository;
import org.personal.user_service.user.response.ResponseUser;
import org.personal.user_service.user.request.RequestRegist;
import org.personal.user_service.user.service.UserServiceImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class UserServiceApplicationTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    private User user1;
    private User user2;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        user1 = new User();
        user1.setUserEmail("user1@test.com");
        user1.setUserNickname("user1");
        user1.setUserEnrollDate(LocalDateTime.now().minusDays(1));
        user1.setUserDeleteDate(null);
        user1.setUserIsDeleted(false);
        user1.setUserRole(ROLE.ROLE_USER);

        user2 = new User();
        user2.setUserEmail("user2@test.com");
        user2.setUserNickname("user2");
        user2.setUserEnrollDate(LocalDateTime.now().minusDays(2));
        user2.setUserDeleteDate(null);
        user2.setUserIsDeleted(true);
        user2.setUserRole(ROLE.ROLE_ADMIN);
    }

    ResponseUser convertToResponseUser(User user) {
        return new ResponseUser(
                user.getUserEmail(),
                user.getUserNickname(),
                DateParsing.LdtToStr(user.getUserEnrollDate()),
                DateParsing.LdtToStr(user.getUserDeleteDate()),
                user.isUserIsDeleted(),
                user.getUserRole());
    }

    @Test
    @DisplayName("사용자 목록 조회")
    public void testGetUserList() {
        // given
        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

        // when
        List<User> responseUserList = userService.getUserList();

        // then
        assertEquals(2, responseUserList.size());

        // user 1
        ResponseUser responseUser1 = convertToResponseUser(responseUserList.get(0));
        assertEquals("user1@test.com", responseUser1.userEmail());
        assertEquals("user1", responseUser1.userNickName());
        assertEquals(DateParsing.LdtToStr(user1.getUserEnrollDate()), responseUser1.userEnrollDate());
        assertEquals(DateParsing.LdtToStr(user1.getUserDeleteDate()), responseUser1.userDeleteDate());
        assertEquals(user1.isUserIsDeleted(), responseUser1.userIsDeleted());
        assertEquals("ROLE_USER", responseUser1.userRole().name());

        // user 2
        ResponseUser responseUser2 = convertToResponseUser(responseUserList.get(1));
        assertEquals("user2@test.com", responseUser2.userEmail());
        assertEquals("user2", responseUser2.userNickName());
        assertEquals(DateParsing.LdtToStr(user2.getUserEnrollDate()), responseUser2.userEnrollDate());
        assertEquals(DateParsing.LdtToStr(user2.getUserDeleteDate()), responseUser2.userDeleteDate());
        assertEquals(user2.isUserIsDeleted(), responseUser2.userIsDeleted());
        assertEquals("ROLE_ADMIN", responseUser2.userRole().name());
    }

    @Test
    @DisplayName("사용자 목록 조회 - 없을 경우")
    public void testGetUserList_Empty() {
        // given
        when(userRepository.findAll()).thenReturn(Collections.emptyList());

        // when
        List<User> responseUserList = userService.getUserList();

        // then
        assertEquals(0, responseUserList.size());
    }

    @Test
    @DisplayName("사용자 회원가입")
    public void testRegist() {
        // given
        RequestRegist requestRegist = new RequestRegist(
                "register@test.com",
                "register",
                "1234",
                ROLE.ROLE_USER.name()
        );
        User user = new User();
        user.setUserEmail("register@test.com");
        user.setUserNickname("register");
        user.setUserPassword("changed_password");
        user.setUserEnrollDate(LocalDateTime.now());
        user.setUserDeleteDate(null);
        user.setUserIsDeleted(false);
        user.setUserRole(ROLE.ROLE_USER);
        when(bCryptPasswordEncoder.encode("1234")).thenReturn("changed_password");
        when(userRepository.save(any(User.class))).thenReturn(user);

        // when
        boolean result = userService.registUser(requestRegist);

        // then
        assertTrue(result);
        assertNotEquals(requestRegist.userPassword(),user.getUserPassword());
    }

    @Test
    @DisplayName("사용자 회원가입-실패")
    public void testRegist_failed() {
        // given
        RequestRegist requestRegist = new RequestRegist(
                "register@test.com",
                "register",
                null,
                null
        );
        User user = new User();
        user.setUserEmail("register@test.com");
        user.setUserNickname("register");
        user.setUserPassword("changed_password");
        user.setUserEnrollDate(LocalDateTime.now());
        user.setUserDeleteDate(null);
        user.setUserIsDeleted(false);
        user.setUserRole(ROLE.ROLE_USER);

        when(bCryptPasswordEncoder.encode("1234")).thenReturn("changed_password");
        when(userRepository.save(any(User.class))).thenReturn(user);

        try {
            // when
            userService.registUser(requestRegist);
        }catch (InvalidRequestException e){
            //then
            assertEquals(e.getMessage(),"회원가입 실패");
        }


    }
}
