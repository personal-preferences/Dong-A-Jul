package org.personal.user_service.user.service;

import org.personal.user_service.config.DateParsing;
import org.personal.user_service.user.domain.User;
import org.personal.user_service.user.etc.ROLE;
import org.personal.user_service.user.exception.InvalidRequestException;
import org.personal.user_service.user.exception.NotFoundException;
import org.personal.user_service.user.repository.UserRepository;
import org.personal.user_service.user.request.RequestRegist;
import org.personal.user_service.user.request.RequestUpdatePassword;
import org.personal.user_service.user.response.ResponseUser;
import org.personal.user_service.user.response.ResponseUserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional(readOnly = true)
    public List<ResponseUser> getUserList() {

        List<User> userList = userRepository.findAll();
        return userList.stream().map(this::convertToResponseUser).toList();
    }

    @Override
    @Transactional(readOnly = false)
    public boolean registUser(RequestRegist requestRegist)  {
        try {
            if (isrequestRegistNull(requestRegist)) {
                throw new InvalidRequestException("잘못된 회원가입");
            }
            if(userRepository.existsByUserEmail(requestRegist.userEmail())){
                throw new InvalidRequestException("이메일 중복");
            }
            if(userRepository.existsByUserNickname(requestRegist.userNickname())){
                throw new InvalidRequestException("닉네임 중복");
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
            throw new InvalidRequestException(e.getMessage());
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
    @Transactional(readOnly = true)
    public ResponseUser getUser(Long userId) {
        User user= userRepository.findById(userId)
                .orElseThrow(()-> new NotFoundException("잘못된 회원 번호"));
        return convertToResponseUser(user);
    }

    @Override
    @Transactional(readOnly = false)
    public void putUserPassword(RequestUpdatePassword requestUpdatePassword) {
        User user = userRepository.findById(requestUpdatePassword.userId())
                .orElseThrow(()->new NotFoundException("잘못된 회원 번호"));
        user.setUserPassword(bCryptPasswordEncoder.encode(requestUpdatePassword.userPassword()));
        userRepository.save(user);
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public ResponseUserDetail getUserByEmail(String userEmail) {
        User user = userRepository.findByUserEmail(userEmail);
        if (user==null)
            throw new NotFoundException("회원 정보 없음");
        return convertToResponseUserDetail(user);
    }


    private ResponseUserDetail convertToResponseUserDetail(User user) {
        return new ResponseUserDetail(user);
    }


    // User to ResponseUser
    private ResponseUser convertToResponseUser(User user) {

        return new ResponseUser(user);
    }

}
