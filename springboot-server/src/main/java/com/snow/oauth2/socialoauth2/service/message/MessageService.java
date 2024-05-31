package com.snow.oauth2.socialoauth2.service.message;

import com.snow.oauth2.socialoauth2.dto.request.chat.ChatDto;
import com.snow.oauth2.socialoauth2.model.chat.Message;
import org.springframework.messaging.MessageHeaders;

public interface MessageService {
    ChatDto.MessageDto sendMessage(String chatId, ChatDto.MessageDto messageDto, String senderId);
    void markMessageAsRead(String chatId, String messageId, String id);
    MessageHeaders getMessageById(String messageId);
}
