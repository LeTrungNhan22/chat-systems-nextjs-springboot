package com.snow.oauth2.socialoauth2.service.chat;


import com.snow.oauth2.socialoauth2.model.chat.Chat;

import java.util.List;

public interface ChatService {
    Chat findOrCreateChat(String userId, String friendId);

    Chat getChatById(String chatId);

    List<Chat> getListConversationByUserId(String userId);
}
