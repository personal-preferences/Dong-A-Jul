package org.personal.user_service.user.service;

import org.personal.user_service.config.DateParsing;
import org.personal.user_service.user.domain.User;
import org.personal.user_service.user.etc.ROLE;
import org.personal.user_service.user.exception.InvalidRequestException;
import org.personal.user_service.user.exception.NotFoundException;
import org.personal.user_service.user.repository.UserRepository;
import org.personal.user_service.user.request.RequestRegist;
import org.personal.user_service.user.response.ResponseUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public List<User> getUserList() {
        return userRepository.findAll();
    }

    @Override
    public boolean registUser(RequestRegist requestRegist)  {
        try {
            if (isrequestRegistNull(requestRegist)) {
                throw new InvalidRequestException("잘못된 회원가입");
            }
            User user = new User();
            user.setUserEmail(requestRegist.userEmail());
            user.setUserNickname(requestRegist.userNickname());
            user.setUserPassword(bCryptPasswordEncoder.encode(requestRegist.userPassword()));
            user.setUserRole(ROLE.valueOf(requestRegist.userRole()));
            user.setUserEnrollDate(LocalDateTime.now());
            user.setUserDeleteDate(null);
            user.setUserIsDeleted(false);

            userRepository.save(user);
        } catch (Exception e) {
            throw new InvalidRequestException("회원가입 실패");
        }

        return true;
    }

    private boolean isrequestRegistNull(RequestRegist requestRegist) {
        return requestRegist.userEmail() == null
                || requestRegist.userRole() == null
                || requestRegist.userNickname() == null
                || requestRegist.userPassword() == null;
    }

    @Override
    public User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(()-> new NotFoundException("잘못된 회원 번호"));
    }

}
