package com.snow.oauth2.socialoauth2.dto.request.chat;

import com.snow.oauth2.socialoauth2.model.chat.MessageStatus;
import com.snow.oauth2.socialoauth2.model.chat.MessageType;
import com.snow.oauth2.socialoauth2.model.user.ProviderType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatDto {
    private String id; // ID của cuộc trò chuyện
    private List<ParticipantDto> participants; // Thông tin người tham gia
    private boolean isGroupChat;
    private String groupName; // Nếu là nhóm chat
    private MessageDto lastMessage; // Thông tin tin nhắn cuối cùng
    private Map<String, Integer> unreadMessagesCount; // Số lượng tin nhắn chưa đọc cho mỗi user
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String adminId; // ID của người tạo nhóm chat

    @Data
    public static class ParticipantDto {
        private String id;
        private String username;
        private String email;
        private String imageUrl;
        private ProviderType providerType;
    }

    @Data
    public static class MessageDto {
        private String senderId;    // ID của người gửi// Tên của người gửi (tùy chọn)
        private String receiverId;  // ID của người nhận (nếu là tin nhắn riêng tư)
        private String messageContent;
        private MessageType type;
    }
}
