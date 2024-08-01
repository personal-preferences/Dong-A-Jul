package org.personal.user_service.user.service;

import org.personal.user_service.config.DateParsing;
import org.personal.user_service.user.domain.User;
import org.personal.user_service.user.etc.ROLE;
import org.personal.user_service.user.exception.InvalidRequestException;
import org.personal.user_service.user.exception.NotFoundException;
import org.personal.user_service.user.repository.UserRepository;
import org.personal.user_service.user.response.ResponseUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
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
    public List<ResponseUser> getUserList() {
        List<User> userList = userRepository.findAll();
        List<ResponseUser> responseUserList = new ArrayList<>();
        if (userList.isEmpty())
            return Collections.emptyList();
        userList.forEach(user -> responseUserList.add(
                new ResponseUser(
                        user.getUserEmail(),
                        user.getUserNickname(),
                        DateParsing.LdtToStr(user.getUserEnrollDate()),
                        DateParsing.LdtToStr(user.getUserDeleteDate()),
                        user.isUserIsDeleted(),
                        user.getUserRole()
                )));
        return responseUserList;
    }

    @Override
    public boolean registUser(RequestRegist requestRegist) throws InvalidRequestException {
        try {
            if (isrequestRegistNull(requestRegist)) {
                throw new InvalidRequestException("Bad Request of Regist");
            }
            System.out.println("requestRegist = " + requestRegist);
            User user = new User();
            user.setUserEmail(requestRegist.userEmail());
            user.setUserNickname(requestRegist.userNickname());
            user.setUserPassword(bCryptPasswordEncoder.encode(requestRegist.userPassword()));
            user.setUserRole(requestRegist.userRole());
            user.setUserEnrollDate(LocalDateTime.now());
            user.setUserDeleteDate(null);
            user.setUserIsDeleted(false);

            System.out.println("user = " + user);
            userRepository.save(user);
        } catch (Exception e) {
            System.err.println("Regist Failed!!!!");
            e.printStackTrace(); // Print the stack trace for better debugging
            return false;
        }

        return true;
    }

    private boolean isrequestRegistNull(RequestRegist requestRegist) {
        return requestRegist.userEmail() != null
                && requestRegist.userRole() != null
                && requestRegist.userNickname() != null
                && requestRegist.userPassword() != null;
    }

    @Override
    public User getUser(int userId) {
        return userRepository.findById(userId)
                .orElseThrow(()-> new NotFoundException("User Not Found"));
    }
}
