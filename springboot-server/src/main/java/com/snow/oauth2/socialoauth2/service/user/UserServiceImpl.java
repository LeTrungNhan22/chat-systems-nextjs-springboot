package com.snow.oauth2.socialoauth2.service.user;


import com.snow.oauth2.socialoauth2.dto.request.user.UserDto;
import com.snow.oauth2.socialoauth2.dto.request.user.UserFilterRequestDto;
import com.snow.oauth2.socialoauth2.exception.notfoud.ResourceNotFoundException;
import com.snow.oauth2.socialoauth2.model.user.User;
import com.snow.oauth2.socialoauth2.repository.FriendRepository;
import com.snow.oauth2.socialoauth2.repository.UserRepository;
import com.snow.oauth2.socialoauth2.security.UserPrincipal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    FriendRepository friendRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with email : " + email)
                );
        return UserPrincipal.create(user);
    }

    @Override
    public UserDetails loadUserById(String id) {
        User user = userRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        return UserPrincipal.create(user);
    }

    @Override
    public List<UserDto> filterUsers(UserFilterRequestDto filterRequestDto) {
        if (filterRequestDto == null || filterRequestDto.getEmail() == null || filterRequestDto.getEmail().isEmpty()) {
            List<User> allUsers = userRepository.findAll();
            return allUsers.stream()
                    .map(this::convertToUserDto)
                    .collect(Collectors.toList());
        } else {
            String emailFilter = filterRequestDto.getEmail();
            List<User> filteredUsers = userRepository.findByEmailContainingIgnoreCase(emailFilter);
            return filteredUsers.stream()
                    .map(this::convertToUserDto)
                    .collect(Collectors.toList());
        }
    }

    @Override
    public UserDto getUserById(String id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", id)
        );
        return convertToUserDto(user);
    }

    @Override
    public Page<User> searchUsers(String query, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return userRepository.searchUsersByEmailOrId(query, pageable);
    }


    private UserDto convertToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        userDto.setImageUrl(user.getImageUrl());
        userDto.setProviderType(user.getProviderType());
        userDto.setProviderId(user.getProviderId());
        userDto.setCreatedAt(user.getCreatedAt());
        userDto.setUpdatedAt(user.getUpdatedAt());
        return userDto;
    }
}
