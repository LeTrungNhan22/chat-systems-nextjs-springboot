package com.snow.oauth2.socialoauth2.service.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.snow.oauth2.socialoauth2.dto.request.chat.MessageRequestDto;
import com.snow.oauth2.socialoauth2.dto.response.MessageResponseDto;

public interface MessageService {

    MessageResponseDto sendMessage(String chatId, MessageRequestDto messageRequestDto, String id) throws JsonProcessingException;
}
