package com.snow.oauth2.socialoauth2.dto.request.friend;

import com.snow.oauth2.socialoauth2.model.friend.FriendshipStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class FriendStatusUpdateRequestDto {
    @NotNull
    private FriendshipStatus status;

}
