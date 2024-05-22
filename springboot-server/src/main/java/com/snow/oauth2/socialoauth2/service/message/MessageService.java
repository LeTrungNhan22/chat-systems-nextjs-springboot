package com.snow.oauth2.socialoauth2.service.message;

import com.snow.oauth2.socialoauth2.dto.request.chat.ChatDto;
import com.snow.oauth2.socialoauth2.model.chat.Message;

public interface MessageService {
    Message sendMessage(String chatId, ChatDto.MessageDto messageDto);
}
