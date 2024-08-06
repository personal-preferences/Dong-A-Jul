package org.personal.user_service.user.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.personal.user_service.user.request.RequestLogin;
import org.personal.user_service.user.response.ResponseToken;
import org.springframework.security.oauth2.core.user.OAuth2User;

public interface LoginService {

    HttpServletResponse login(RequestLogin requestLogin, HttpServletResponse response);

    void logout(HttpServletRequest req);

    ResponseToken processOAuth2User(OAuth2User oAuth2User);
}
