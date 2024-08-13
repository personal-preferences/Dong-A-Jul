package org.personal.user_service.oauth;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oauth2User = super.loadUser(userRequest);

        // 사용자 정보 매핑
        Map<String, Object> attributes = oauth2User.getAttributes();

        // Kakao의 사용자 정보 API에서 반환된 정보가 중첩된 구조일 경우 이를 풀어헤칩니다.
        Map<String, Object> account = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) account.get("profile");

        String nickname = (String) profile.get("nickname");
        String email = (String) account.get("email");

        // 사용자 정보를 반환할 때 추가할 수 있습니다.
        // 예시: CustomOAuth2User를 만들어 필요한 정보를 담아 반환합니다.
        return new CustomOAuth2User(oauth2User.getAuthorities(), attributes, nickname, email);
    }
}
