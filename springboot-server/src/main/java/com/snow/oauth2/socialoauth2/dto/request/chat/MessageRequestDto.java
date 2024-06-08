package com.snow.oauth2.socialoauth2.dto.request.chat;

import com.snow.oauth2.socialoauth2.model.chat.MessageType;
import lombok.Data;

import java.util.List;

@Data
public class MessageRequestDto {
    private String chatId;          // ID của cuộc trò chuyện
    private String content;         // Nội dung tin nhắn dạng text
    private MessageType messageType; // Loại tin nhắn (TEXT, IMAGE, VIDEO)
    private String mediaBase64;     // Dữ liệu media ở dạng Base64 (nếu có)
    private List<String> keywords;  // Danh sách từ khóa (có thể để trống)
}
