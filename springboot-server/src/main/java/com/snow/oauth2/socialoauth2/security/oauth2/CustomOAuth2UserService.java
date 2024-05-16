package com.snow.oauth2.socialoauth2.security.oauth2;


import com.snow.oauth2.socialoauth2.exception.OAuth2AuthenticationProcessingException;
import com.snow.oauth2.socialoauth2.model.ProviderType;
import com.snow.oauth2.socialoauth2.model.User;
import com.snow.oauth2.socialoauth2.repository.UserRepository;
import com.snow.oauth2.socialoauth2.security.UserPrincipal;
import com.snow.oauth2.socialoauth2.security.oauth2.user.OAuth2UserInfo;
import com.snow.oauth2.socialoauth2.security.oauth2.user.OAuth2UserInfoFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userOAuth2Request) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userOAuth2Request);
        try {
            return processOAuth2User(userOAuth2Request, oAuth2User);
        } catch (AuthenticationException ex) {
            throw new OAuth2AuthenticationProcessingException(ex.getMessage());
        } catch (Exception ex) {
            // Throwing an instance of AuthenticationException will trigger the OAuth2AuthenticationFailureHandler
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }

    }

    private OAuth2User processOAuth2User(OAuth2UserRequest userOAuth2Request, OAuth2User oAuth2User) throws OAuth2AuthenticationProcessingException {
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(userOAuth2Request.getClientRegistration().getRegistrationId(), oAuth2User.getAttributes());

        if (!StringUtils.hasText(oAuth2UserInfo.getEmail())) {
            throw new OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider!!");
        }

        Optional<User> userOptional = userRepository.findByEmail(oAuth2UserInfo.getEmail());
        User user;

        if (userOptional.isPresent()) {
            user = userOptional.get();
            if (!user.getProviderType().equals(ProviderType.valueOf(userOAuth2Request.getClientRegistration().getRegistrationId().toUpperCase()))) {
                throw new OAuth2AuthenticationProcessingException("Looks like you're signed up with " +
                                                                  user.getProviderType() + " account. Please use your " + user.getProviderType() +
                                                                  " account to login.");
            }
            user = updateExistingUser(user, oAuth2UserInfo);
        } else {
            user = registerNewUser(userOAuth2Request, oAuth2UserInfo);
        }


        return UserPrincipal.create(user, oAuth2UserInfo.getAttributes());
    }

    private User registerNewUser(OAuth2UserRequest userOAuth2Request, OAuth2UserInfo oAuth2UserInfo) {
        User user = new User();
        user.setProviderType(ProviderType.valueOf(userOAuth2Request.getClientRegistration().getRegistrationId().toUpperCase()));
        user.setProviderId(oAuth2UserInfo.getId());
        user.setUsername(oAuth2UserInfo.getName());
        user.setEmail(oAuth2UserInfo.getEmail());
        user.setImageUrl(oAuth2UserInfo.getImageUrl());
        user.setCreatedAt(java.time.LocalDateTime.now());
        user.setUpdatedAt(java.time.LocalDateTime.now());
        return userRepository.save(user);

    }

    private User updateExistingUser(User existingUser, OAuth2UserInfo oAuth2UserInfo) {
        existingUser.setUsername(oAuth2UserInfo.getName());
        existingUser.setImageUrl(oAuth2UserInfo.getImageUrl());
        existingUser.setUpdatedAt(java.time.LocalDateTime.now());
        return userRepository.save(existingUser);
    }

}
