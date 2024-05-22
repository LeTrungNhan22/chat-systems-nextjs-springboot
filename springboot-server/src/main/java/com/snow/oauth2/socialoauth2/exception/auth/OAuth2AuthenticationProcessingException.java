package com.snow.oauth2.socialoauth2.exception.auth;

import org.springframework.security.core.AuthenticationException;

public class OAuth2AuthenticationProcessingException extends AuthenticationException {
    public OAuth2AuthenticationProcessingException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public OAuth2AuthenticationProcessingException(String msg) {
        super(msg);
    }
}
