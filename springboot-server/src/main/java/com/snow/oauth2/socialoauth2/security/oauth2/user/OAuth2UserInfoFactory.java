package com.snow.oauth2.socialoauth2.security.oauth2.user;

import com.snow.oauth2.socialoauth2.exception.auth.OAuth2AuthenticationProcessingException;
import com.snow.oauth2.socialoauth2.model.user.ProviderType;

import java.util.Map;

public class OAuth2UserInfoFactory {
    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
        if (registrationId.equalsIgnoreCase(ProviderType.GOOGLE.toString())) {
            return new GoogleOAuth2UserInfo(attributes);
//        } else if (registrationId.equalsIgnoreCase(ProviderType.GITHUB.toString())) {
//            return new GithubOAuth2UserInfo(attributes);
        } else {
            throw new OAuth2AuthenticationProcessingException("Sorry! Login with " + registrationId + " is not supported yet.");
        }
    }

}
