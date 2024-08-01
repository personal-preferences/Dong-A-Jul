package org.personal.user_service.user.service;

import org.personal.user_service.config.DateParsing;
import org.personal.user_service.user.domain.User;
import org.personal.user_service.user.repository.UserRepository;
import org.personal.user_service.user.response.ResponseUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class UserServiceImpl  implements UserService{

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public List<ResponseUser> getUserList() {
        List<User> userList = userRepository.findAll();
        List<ResponseUser> responseUserList= new ArrayList<>();
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
}
