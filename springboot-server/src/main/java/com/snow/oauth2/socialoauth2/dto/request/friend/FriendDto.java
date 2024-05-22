package com.snow.oauth2.socialoauth2.dto.request.friend;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FriendDto {
    private String senderId;
    private String receiverId;
    private String status;
}
