package com.snow.oauth2.socialoauth2.service.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.snow.oauth2.socialoauth2.dto.request.chat.MessageRequestDto;
import com.snow.oauth2.socialoauth2.dto.response.MessageResponseDto;
import com.snow.oauth2.socialoauth2.exception.message.MediaSizeLimitExceededException;
import com.snow.oauth2.socialoauth2.exception.notfoud.ChatNotFoundException;
import com.snow.oauth2.socialoauth2.exception.notfoud.UserNotFoundException;
import com.snow.oauth2.socialoauth2.model.chat.Chat;
import com.snow.oauth2.socialoauth2.model.chat.Message;
import com.snow.oauth2.socialoauth2.model.chat.MessageType;
import com.snow.oauth2.socialoauth2.model.user.User;
import com.snow.oauth2.socialoauth2.repository.ChatRepository;
import com.snow.oauth2.socialoauth2.repository.MessageRepository;
import com.snow.oauth2.socialoauth2.repository.UserRepository;
import com.snow.oauth2.socialoauth2.service.redis.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final ChatRepository chatRepository;
    private final UserRepository userRepository;
    private final SimpMessagingTemplate messagingTemplate;
    private final RedisService redisService;


    @Override
    public MessageResponseDto sendMessage(String chatId, MessageRequestDto messageRequestDto, String currentUserId) throws JsonProcessingException {
        // 1. Kiểm tra quyền truy cập chat
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new ChatNotFoundException("Chat not found with id: " + chatId));

        User currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + currentUserId));

        if (!chat.getParticipants().contains(currentUser)) {
            throw new AccessDeniedException("You don't have permission to send messages in this chat.");
        }

        // 2. Tạo đối tượng Message
        Message message = new Message();
        message.setSender(currentUser);
        message.setChat(chat);
        message.setMessageType(messageRequestDto.getMessageType());

        // 3. Xử lý nội dung tin nhắn
        if (messageRequestDto.getMessageType() == MessageType.TEXT) {
            message.setMessageContent(messageRequestDto.getContent());
        } else if (messageRequestDto.getMessageType() == MessageType.IMAGE || messageRequestDto.getMessageType() == MessageType.VIDEO) {
            byte[] mediaData = Base64.getDecoder().decode(messageRequestDto.getMediaBase64());
            long mediaSize = mediaData.length;
            long maxMediaSize = 10 * 1024 * 1024; // 10MB

            if (mediaSize > maxMediaSize) {
                throw new MediaSizeLimitExceededException("File size exceeds the limit of 10MB.");
            }

            String mediaUrl = redisService.storeMedia(mediaData);
            message.setMediaUrl(mediaUrl);
        }

        // 4. Lưu tin nhắn vào MongoDB
        message = messageRepository.save(message);

        // 5. Gửi tin nhắn qua WebSocket
        try {
            messagingTemplate.convertAndSend("/topic/chats/" + chatId, messageRequestDto);

        } catch (Exception e) {
            log.error("Error when sending message to chat: {}", chatId, e);

        }
        return messageConvertDto(message);
    }

    private static MessageResponseDto messageConvertDto(Message message) {
        MessageResponseDto responseDto = new MessageResponseDto();
        responseDto.setMessageId(message.getId());
        responseDto.setSenderId(message.getSender().getId());
        responseDto.setMessageContent(message.getMessageContent());
        responseDto.setMediaUrl(message.getMediaUrl());
        responseDto.setTimestamp(message.getTimestamp());
        responseDto.setStatus(message.getStatus());
        responseDto.setMessageType(message.getMessageType());
        return responseDto;
    }

}


