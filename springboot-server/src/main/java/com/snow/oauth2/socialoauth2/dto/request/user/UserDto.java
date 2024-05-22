package com.snow.oauth2.socialoauth2.dto.request.user;

import com.snow.oauth2.socialoauth2.model.user.ProviderType;
import lombok.Data;
import net.minidev.json.annotate.JsonIgnore;

import java.time.LocalDateTime;
@Data
public class UserDto {
    private String id;
    private String username;
    private String email;
    @JsonIgnore
    private String password = null;
    private ProviderType providerType;
    private String imageUrl;
    private String providerId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
