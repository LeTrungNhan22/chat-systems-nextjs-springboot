package com.snow.oauth2.socialoauth2.service.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.snow.oauth2.socialoauth2.dto.request.chat.MessageRequestDto;
import com.snow.oauth2.socialoauth2.dto.response.MessageResponseDto;
import com.snow.oauth2.socialoauth2.exception.message.MediaSizeLimitExceededException;
import com.snow.oauth2.socialoauth2.exception.notfoud.ChatNotFoundException;
import com.snow.oauth2.socialoauth2.exception.notfoud.UserNotFoundException;
import com.snow.oauth2.socialoauth2.model.chat.Chat;
import com.snow.oauth2.socialoauth2.model.chat.Message;
import com.snow.oauth2.socialoauth2.model.chat.MessageStatus;
import com.snow.oauth2.socialoauth2.model.chat.MessageType;
import com.snow.oauth2.socialoauth2.model.user.User;
import com.snow.oauth2.socialoauth2.repository.ChatRepository;
import com.snow.oauth2.socialoauth2.repository.MessageRepository;
import com.snow.oauth2.socialoauth2.repository.UserRepository;
import com.snow.oauth2.socialoauth2.security.TokenProvider;
import com.snow.oauth2.socialoauth2.service.redis.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
    private final TokenProvider tokenProvider;

    private static final long MAX_MEDIA_SIZE = 10 * 1024 * 1024; // 10MB

    @Override
    public MessageResponseDto sendMessage(String chatId, MessageRequestDto messageRequestDto, String authorizationHeader) {
        String currentUserId = getCurrentUserIdFromJwt(authorizationHeader);

        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new ChatNotFoundException("Chat not found with id: " + chatId));

        User currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + currentUserId));

        validateChatPermission(chat, currentUser);

        Message message = createMessage(currentUser, chat, messageRequestDto);
        processMediaContent(messageRequestDto, message);
        message = messageRepository.save(message);
        MessageResponseDto responseDto = messageConvertDto(message);

        sendMessageViaWebSocket(chatId, responseDto);
        return responseDto;
    }

    private String getCurrentUserIdFromJwt(String authorizationHeader) {
        String jwt = getJwtFromRequest(authorizationHeader);
        if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
            return tokenProvider.getUserIdFromToken(jwt);
        } else {
            return null;
        }
    }

    private void validateChatPermission(Chat chat, User currentUser) {
        if (!chat.getParticipants().contains(currentUser)) {
            throw new AccessDeniedException("You don't have permission to send messages in this chat.");
        }
    }
    private Message createMessage(User currentUser, Chat chat, MessageRequestDto messageRequestDto) {
        Message message = new Message();
        message.setSender(currentUser);
        message.setChat(chat);
        message.setMessageType(messageRequestDto.getMessageType());
        message.setStatus(MessageStatus.SENT);
        if (messageRequestDto.getMessageType() == MessageType.TEXT) {
            message.setMessageContent(messageRequestDto.getContent());
        }
        return message;
    }

    private void processMediaContent(MessageRequestDto messageRequestDto, Message message) {
        if (messageRequestDto.getMessageType() == MessageType.IMAGE || messageRequestDto.getMessageType() == MessageType.VIDEO) {
            byte[] mediaData = Base64.getDecoder().decode(messageRequestDto.getMediaBase64());
            if (mediaData.length > MAX_MEDIA_SIZE) {
                throw new MediaSizeLimitExceededException("File size exceeds the limit of 10MB.");
            }
            String mediaUrl = redisService.storeMedia(mediaData);
            message.setMediaUrl(mediaUrl);
        }
    }
    private void sendMessageViaWebSocket(String chatId, MessageResponseDto responseDto) {
        try {
            messagingTemplate.convertAndSend("/topic/chats/" + chatId, responseDto);
        } catch (Exception e) {
            log.error("Error when sending message to chat: {}", chatId, e);
        }
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

    private String getJwtFromRequest(String authorizationHeader) {
        if (StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        return null;
    }
}


