package com.snow.oauth2.socialoauth2.configuration.security;


import com.snow.oauth2.socialoauth2.security.RestAuthenticationEntryPoint;
import com.snow.oauth2.socialoauth2.security.TokenAuthenticationFilter;
import com.snow.oauth2.socialoauth2.security.oauth2.CustomOAuth2UserService;
import com.snow.oauth2.socialoauth2.security.oauth2.HttpCookieOAuth2AuthorizationRequestRepository;
import com.snow.oauth2.socialoauth2.security.oauth2.OAuth2AuthenticationFailureHandler;
import com.snow.oauth2.socialoauth2.security.oauth2.OAuth2AuthenticationSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)

public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
    private final OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;
    private final HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;

    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter() {
        return new TokenAuthenticationFilter();
    }

    /*
         By default, Spring OAuth2 uses HttpSessionOAuth2AuthorizationRequestRepository to save
         the authorization request. But, since our service is stateless, we can't save it in
         the session. We'll save the request in a Base64 encoded cookie instead.
       */
    @Bean
    public HttpCookieOAuth2AuthorizationRequestRepository cookieAuthorizationRequestRepository() {
        return new HttpCookieOAuth2AuthorizationRequestRepository();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors(AbstractHttpConfigurer::disable);
        http.sessionManagement(sessionManagement -> sessionManagement
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.csrf(AbstractHttpConfigurer::disable);
        http.formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable);
        http.exceptionHandling(exceptionHandling -> exceptionHandling
                .authenticationEntryPoint(new RestAuthenticationEntryPoint()));
        http.authorizeHttpRequests(authorizeRequests -> authorizeRequests
                .requestMatchers("/",
                        "/error",
                        "/favicon.ico",
                        "/*/*.png",
                        "/*/*.gif",
                        "/*/*.svg",
                        "/*/*.jpg",
                        "/*/*.html",
                        "/*/*.css",
                        "/*/*.js",
                        "/api/v1/**",
                        "/swagger-ui/**",
                        "/swagger-resources/**",
                        "/v2/api-docs",
                        "/v3/api-docs",
                        "/webjars/**",
                        "/v3/api-docs/**",
                        "/configuration/ui",
                        "/configuration/security",
                        "/swagger-resources",
                        "/swagger-resources/**",
                        "/swagger-ui.html",
                        "/ws/**"


                )
                .permitAll()
                .requestMatchers("/auth/**", "/oauth2/**","ws/**")
                .permitAll()
                .anyRequest()
                .authenticated());


        http.oauth2Login(oauth2 -> oauth2
                .authorizationEndpoint(authorizationEndpoint -> authorizationEndpoint
                        .baseUri("/oauth2/authorize")
                        .authorizationRequestRepository(cookieAuthorizationRequestRepository()))
                .redirectionEndpoint(redirectionEndpoint -> redirectionEndpoint
                        .baseUri("/oauth2/callback/*"))
                .userInfoEndpoint(userInfoEndpoint -> userInfoEndpoint
                        .userService(customOAuth2UserService))
                .successHandler(oAuth2AuthenticationSuccessHandler)
                .failureHandler(oAuth2AuthenticationFailureHandler));
        // Add our custom Token based authentication filter
        http.addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}