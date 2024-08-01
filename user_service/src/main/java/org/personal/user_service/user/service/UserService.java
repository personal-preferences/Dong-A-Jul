package org.personal.user_service.user.service;

import org.personal.user_service.user.response.ResponseUser;

import java.util.List;

public interface UserService {

    List<ResponseUser> getUserList();

    boolean registUser(RequestRegist requestRegist);
}
