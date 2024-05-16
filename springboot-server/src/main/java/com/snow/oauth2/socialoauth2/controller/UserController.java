package com.snow.oauth2.socialoauth2.controller;


import com.snow.oauth2.socialoauth2.exception.ResourceNotFoundException;
import com.snow.oauth2.socialoauth2.model.User;
import com.snow.oauth2.socialoauth2.repository.UserRepository;
import com.snow.oauth2.socialoauth2.security.CurrentUser;
import com.snow.oauth2.socialoauth2.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserRepository userRepository;

    @GetMapping("/profile")
    @PreAuthorize("hasRole('USER')")
    public User getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
        return userRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
    }


}
