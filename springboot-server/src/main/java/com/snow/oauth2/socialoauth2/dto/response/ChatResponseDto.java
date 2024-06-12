package com.snow.oauth2.socialoauth2.dto.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
public class ChatResponseDto {
    private String id;
    private List<String> participantIds; // Chứa danh sách ID người tham gia
    private boolean isGroupChat;
    private String groupName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private MessageResponseDto lastMessage; // DTO của tin nhắn cuối cùng
    private Map<String, Integer> unreadMessagesCount;
    private String adminId;  // ID của admin (nếu có)
    private String avatar;
}