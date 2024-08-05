package org.personal.user_service.user.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.personal.user_service.user.request.RequestLogin;

public interface LoginService {

    HttpServletResponse login(RequestLogin requestLogin, HttpServletResponse response);

    void logout(HttpServletRequest req);

}
