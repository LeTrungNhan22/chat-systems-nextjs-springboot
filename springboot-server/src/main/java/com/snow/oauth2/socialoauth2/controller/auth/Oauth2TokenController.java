package com.snow.oauth2.socialoauth2.controller.auth;


import com.snow.oauth2.socialoauth2.configuration.base.AppConfig;
import com.snow.oauth2.socialoauth2.model.user.ProviderType;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/oauth2")
@RequiredArgsConstructor
public class Oauth2TokenController {
    private final AppConfig appConfig;

    @PostMapping("/url")
    public String getOauth2Url(@RequestParam("provider") ProviderType providerType) {
        return "localhost:8080/oauth2/authorize/"
               + providerType.toString().toLowerCase()
               + "?redirect_uri="
               + appConfig.getAuthorizedRedirectUris().get(0);

    }


}
