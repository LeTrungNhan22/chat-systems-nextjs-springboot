package com.snow.oauth2.socialoauth2.dto.response;

import com.snow.oauth2.socialoauth2.model.friend.FriendStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class FriendStatusUpdateResponseDto {
    private String currentUserId;
    private String requestFriendId;
    private FriendStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
