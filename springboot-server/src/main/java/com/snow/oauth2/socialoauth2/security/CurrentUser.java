package com.snow.oauth2.socialoauth2.security;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Document
@AuthenticationPrincipal
public @interface CurrentUser {
}
