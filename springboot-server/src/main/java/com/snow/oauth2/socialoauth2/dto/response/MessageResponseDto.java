package com.snow.oauth2.socialoauth2.dto.response;

import com.snow.oauth2.socialoauth2.model.chat.MessageStatus;
import com.snow.oauth2.socialoauth2.model.chat.MessageType;
import lombok.Data;

import java.util.List;

@Data
public class MessageResponseDto {
    private String messageId;
    private String senderId;
    private String messageContent;
    private List<String> mediaUrl;
    private long timestamp;
    private MessageStatus status;
    private MessageType messageType;
}
