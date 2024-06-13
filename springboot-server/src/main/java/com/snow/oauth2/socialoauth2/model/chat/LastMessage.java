package com.snow.oauth2.socialoauth2.model.chat;


import com.snow.oauth2.socialoauth2.model.user.User;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;

@Data
@Builder
public class LastMessage {
    @DBRef(lazy = true)
    private User senderId;
    private String content;
    private long timestamp;
    private MessageStatus status;
    private MessageType type;
}
