package com.snow.oauth2.socialoauth2.service.message;

import com.snow.oauth2.socialoauth2.dto.request.chat.ChatDto;
import com.snow.oauth2.socialoauth2.exception.auth.BadRequestException;
import com.snow.oauth2.socialoauth2.exception.notfoud.UserNotFoundException;
import com.snow.oauth2.socialoauth2.model.chat.Chat;
import com.snow.oauth2.socialoauth2.model.chat.Message;
import com.snow.oauth2.socialoauth2.model.chat.MessageStatus;
import com.snow.oauth2.socialoauth2.model.user.User;
import com.snow.oauth2.socialoauth2.repository.ChatRepository;
import com.snow.oauth2.socialoauth2.repository.MessageRepository;
import com.snow.oauth2.socialoauth2.repository.UserRepository;
import com.snow.oauth2.socialoauth2.service.chat.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final ChatRepository chatRepository;
    private final ChatService chatService;


    @Override
    public Message sendMessage(String chatId, ChatDto.MessageDto messageDto, String senderId) {
        User sender = userRepository.findById(senderId).orElseThrow(() -> new UserNotFoundException(senderId));
        Chat chat = chatRepository.findById(chatId).orElseThrow(() -> new BadRequestException("Chat not found with id: " + chatId));

        Message message = new Message();
        message.setReceiver(chatService.getReceiver(chatId, senderId));
        message.setChat(chat);
        message.setSender(sender);
        message.setMessageContent(messageDto.getMessageContent());
        message.setMessageType(messageDto.getType());
        message.setStatus(MessageStatus.DELIVERED);
        message.setTimestamp(LocalDateTime.now());

        return messageRepository.save(message);
    }

    @Override
    public void markMessageAsRead(String chatId, String messageId, String id) {
        Message message = messageRepository.findById(messageId).orElseThrow(() -> new BadRequestException("Message not found with id: " + messageId));
        if (!message.getReceiver().getId().equals(id)) {
            throw new BadRequestException("You are not receiver of this message");
        }
        message.setStatus(MessageStatus.SEEN);
        messageRepository.save(message);
    }

    @Override
    public MessageHeaders getMessageById(String messageId) {
        Message message = messageRepository.findById(messageId).
                orElseThrow(() -> new BadRequestException("Message not found with id: " + messageId));
        return null;
    }
}
