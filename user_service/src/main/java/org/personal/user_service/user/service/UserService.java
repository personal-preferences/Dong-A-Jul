package org.personal.user_service.user.service;

import org.personal.user_service.user.domain.User;
import org.personal.user_service.user.exception.InvalidRequestException;
import org.personal.user_service.user.request.RequestRegist;
import org.personal.user_service.user.request.RequestUpdatePassword;
import org.personal.user_service.user.response.ResponseUser;
import org.personal.user_service.user.response.ResponseUserDetail;

import java.util.List;

public interface UserService {

    List<ResponseUser> getUserList();

    boolean registUser(RequestRegist requestRegist) throws InvalidRequestException;

    ResponseUser getUser(Long userId);

    void putUserPassword(RequestUpdatePassword requestUpdatePassword);

    void deleteUser(Long userId);

    ResponseUserDetail getUserByEmail(String userEmail);

    ResponseUserDetail registKakaoUser(RequestRegist requestRegistUser);

    ResponseUser getMyInfo();

    List<ResponseUser> getUserList(int page);
}
