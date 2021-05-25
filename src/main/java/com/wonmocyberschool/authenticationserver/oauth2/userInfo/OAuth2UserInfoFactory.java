package com.wonmocyberschool.authenticationserver.oauth2.userInfo;

import com.wonmocyberschool.authenticationserver.AuthProvider;
import com.wonmocyberschool.authenticationserver.exception.OAuth2AuthenticationProcessingException;

import java.util.Map;
//github 도 추가하고 싶으면 팩토리에서 깃허브를 구별하고 유저인포 클래스 하나 깃헙에서 주는 응답 보디에 제이슨 보고 만들면 된다.
public class OAuth2UserInfoFactory {
    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
        if(registrationId.equalsIgnoreCase(AuthProvider.google.toString())) {
            return new GoogleOAuth2UserInfo(attributes);
        } else {
            throw new OAuth2AuthenticationProcessingException("Sorry! Login with " + registrationId + " is not supported yet.");
        }
    }
}
