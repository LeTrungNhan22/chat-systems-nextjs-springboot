package com.snow.oauth2.socialoauth2.controller;


import com.snow.oauth2.socialoauth2.dto.request.user.UserDto;
import com.snow.oauth2.socialoauth2.dto.request.user.UserFilterRequestDto;
import com.snow.oauth2.socialoauth2.exception.notfoud.ResourceNotFoundException;
import com.snow.oauth2.socialoauth2.model.user.User;
import com.snow.oauth2.socialoauth2.repository.UserRepository;
import com.snow.oauth2.socialoauth2.security.CurrentUser;
import com.snow.oauth2.socialoauth2.security.UserPrincipal;
import com.snow.oauth2.socialoauth2.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserRepository userRepository;

    private final UserService userService;

    @GetMapping("/profile")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Get current user")
    public User getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
        return userRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
    }

    @GetMapping("/filter")
    @Operation(summary = "Filter users")
    public ResponseEntity<List<UserDto>> filterUsers(@RequestBody(required = false) UserFilterRequestDto filterRequestDto) {
        List<UserDto> filteredUsers = userService.filterUsers(filterRequestDto);
        return ResponseEntity.ok(filteredUsers);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get user by id")
    public ResponseEntity<UserDto> getUserById(@PathVariable String id) {
        UserDto userDto = userService.getUserById(id);
        return ResponseEntity.ok(userDto);
    }


}
