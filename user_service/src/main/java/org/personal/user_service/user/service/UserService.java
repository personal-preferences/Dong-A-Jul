package org.personal.user_service.user.service;

import org.personal.user_service.user.domain.User;
import org.personal.user_service.user.exception.InvalidRequestException;
import org.personal.user_service.user.request.RequestRegist;
import org.personal.user_service.user.response.ResponseUser;

import java.util.List;

public interface UserService {

    List<User> getUserList();

    boolean registUser(RequestRegist requestRegist) throws InvalidRequestException;

    User getUser(Long userId);
}
