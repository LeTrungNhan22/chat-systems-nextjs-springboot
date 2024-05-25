package com.snow.oauth2.socialoauth2.service.chat;


import com.snow.oauth2.socialoauth2.dto.request.chat.ChatDto;
import com.snow.oauth2.socialoauth2.model.chat.Chat;
import com.snow.oauth2.socialoauth2.model.chat.Message;
import com.snow.oauth2.socialoauth2.model.user.User;

public interface ChatService {
    Chat createChat(String userId, String friendId);
    User getReceiver(String chatId, String senderId);
    Chat getChatById(String chatId);
    String getReceiverId(Chat chat, String senderId);
}
