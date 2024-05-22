package com.snow.oauth2.socialoauth2.service.message;

import com.snow.oauth2.socialoauth2.dto.request.chat.ChatDto;
import com.snow.oauth2.socialoauth2.exception.auth.BadRequestException;
import com.snow.oauth2.socialoauth2.exception.user.UserNotFoundException;
import com.snow.oauth2.socialoauth2.model.chat.Chat;
import com.snow.oauth2.socialoauth2.model.chat.Message;
import com.snow.oauth2.socialoauth2.model.user.User;
import com.snow.oauth2.socialoauth2.repository.ChatRepository;
import com.snow.oauth2.socialoauth2.repository.MessageRepository;
import com.snow.oauth2.socialoauth2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService{

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final ChatRepository chatRepository;

    @Override
    public Message sendMessage(String chatId, ChatDto.MessageDto messageDto) {
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new BadRequestException("Chat not found"));

        User sender = userRepository.findById(messageDto.getSenderId())
                .orElseThrow(() -> new UserNotFoundException(messageDto.getSenderId()));

        Message message = new Message();
        message.setSender(sender);
        message.setMessageContent(messageDto.getMessageContent());
        message.setTimeStamp(LocalDateTime.now());
        message.setChat(chat);

        return messageRepository.saveMessage(chat, message);
    }

}
