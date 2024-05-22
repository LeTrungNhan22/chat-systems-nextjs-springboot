package com.snow.oauth2.socialoauth2.dto.request.user;


import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;

@Data
public class UserFilterRequestDto {
    @Indexed
    private String email;
}
