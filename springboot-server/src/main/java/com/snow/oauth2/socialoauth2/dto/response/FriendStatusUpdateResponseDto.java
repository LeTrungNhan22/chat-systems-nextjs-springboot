package com.snow.oauth2.socialoauth2.dto.response;

import com.snow.oauth2.socialoauth2.model.friend.FriendshipStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class FriendStatusUpdateResponseDto {
    private String userId1;
    private String userId2;
    private FriendshipStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
