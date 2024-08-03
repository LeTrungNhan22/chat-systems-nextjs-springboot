package com.snow.oauth2.socialoauth2.service.message;

import com.snow.oauth2.socialoauth2.dto.request.chat.MessageRequestDto;
import com.snow.oauth2.socialoauth2.dto.response.MessageResponseDto;
import com.snow.oauth2.socialoauth2.exception.notfoud.ChatNotFoundException;
import com.snow.oauth2.socialoauth2.exception.notfoud.UserNotFoundException;
import com.snow.oauth2.socialoauth2.model.chat.Chat;
import com.snow.oauth2.socialoauth2.model.chat.LastMessage;
import com.snow.oauth2.socialoauth2.model.message.Message;
import com.snow.oauth2.socialoauth2.model.message.MessageStatus;
import com.snow.oauth2.socialoauth2.model.message.MessageType;
import com.snow.oauth2.socialoauth2.model.user.User;
import com.snow.oauth2.socialoauth2.repository.ChatRepository;
import com.snow.oauth2.socialoauth2.repository.MessageRepository;
import com.snow.oauth2.socialoauth2.repository.UserRepository;
import com.snow.oauth2.socialoauth2.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final ChatRepository chatRepository;
    private final UserRepository userRepository;

    private final SimpMessagingTemplate messagingTemplate;
    private final TokenProvider tokenProvider;

    @Override
    public MessageResponseDto sendMessage(String chatId, MessageRequestDto messageRequestDto, String authorizationHeader) {
        //      Get current user id from JWT
        String currentUserId = getCurrentUserIdFromJwt(authorizationHeader);

        //      validate chat permission
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new ChatNotFoundException("Chat not found with id: " + chatId));

        assert currentUserId != null;
        User currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + currentUserId));

        validateChatPermission(chat, currentUser);

        // Khởi tạo message và lưu vào database
        Message message = createMessage(currentUser, chat, messageRequestDto);
        message = messageRepository.save(message);

        // Chỉ xử lý và lưu media khi là ảnh hoặc video và message đã được lưu
        if ((messageRequestDto.getMessageType() == MessageType.IMAGE || messageRequestDto.getMessageType() == MessageType.VIDEO) && message.getId() != null) {
            message.setMediaUrl(messageRequestDto.getMediaUrl());
        }
        processChatLastMessage(messageRequestDto, currentUser, message, chat);
        chatRepository.save(chat);
        MessageResponseDto responseDto = messageConvertDto(message);
        sendMessageViaWebSocket(chatId, responseDto);
        return responseDto;
    }

    private static void processChatLastMessage(MessageRequestDto messageRequestDto, User currentUser, Message message, Chat chat) {
        LastMessage lastMessage = LastMessage.builder()
                .senderId(currentUser)
                .content(messageRequestDto.getContent())
                .timestamp(message.getTimestamp())
                .status(message.getStatus())
                .type(message.getMessageType())
                .build();

        if (messageRequestDto.getMessageType() == MessageType.IMAGE || messageRequestDto.getMessageType() == MessageType.VIDEO) {
            lastMessage.setContent(currentUser.getId() + " sent a media file");
        }

        chat.setLastMessageByUser(lastMessage);
    }

    @Override
    public Page<Message> findAll(Pageable pageable) {
        return messageRepository.findAll(pageable);
    }

    @Override
    public Page<Message> findByChatId(String chatId, Pageable pageable) {
        return messageRepository.findByChatId(chatId, pageable);
    }

    @Override
    public Message findById(String id) {
        return messageRepository.findById(id)
                .orElseThrow(() -> new ChatNotFoundException("Message not found with id: " + id));
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
        message.setTimestamp(System.currentTimeMillis());
        message.setMessageContent(messageRequestDto.getContent());
        if (messageRequestDto.getMessageType() == MessageType.IMAGE || messageRequestDto.getMessageType() == MessageType.VIDEO) {
            message.setMediaUrl(messageRequestDto.getMediaUrl());
        }
        return message;
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


