package com.snow.oauth2.socialoauth2.service.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.snow.oauth2.socialoauth2.dto.request.chat.MessageRequestDto;
import com.snow.oauth2.socialoauth2.dto.response.MessageResponseDto;
import com.snow.oauth2.socialoauth2.model.chat.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MessageService {

    MessageResponseDto sendMessage(String chatId, MessageRequestDto messageRequestDto, String id) throws JsonProcessingException;

    Page<Message> findAll(Pageable pageable);

    Page<Message> findByChatId(String chatId, Pageable pageable);

    Message findById(String id);
}
