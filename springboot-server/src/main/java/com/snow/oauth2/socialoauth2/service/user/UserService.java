package com.snow.oauth2.socialoauth2.service.user;


import com.snow.oauth2.socialoauth2.dto.request.user.UserDto;
import com.snow.oauth2.socialoauth2.dto.request.user.UserFilterRequestDto;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface UserService extends UserDetailsService {
    UserDetails loadUserByUsername(String email) throws UsernameNotFoundException;

    UserDetails loadUserById(String id);

    List<UserDto> filterUsers(UserFilterRequestDto filterRequestDto);

    UserDto getUserById(String id);
}
