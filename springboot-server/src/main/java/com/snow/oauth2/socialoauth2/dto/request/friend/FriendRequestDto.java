package com.snow.oauth2.socialoauth2.dto.request.friend;

import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class FriendRequestDto {
    @NotNull
    private String friendId;


}