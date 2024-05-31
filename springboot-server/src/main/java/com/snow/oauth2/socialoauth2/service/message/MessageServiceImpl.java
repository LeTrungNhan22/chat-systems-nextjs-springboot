package com.snow.oauth2.socialoauth2.service.message;

import com.snow.oauth2.socialoauth2.dto.mapper.MessageMapper;
import com.snow.oauth2.socialoauth2.dto.request.chat.ChatDto;
import com.snow.oauth2.socialoauth2.exception.notfoud.ChatNotFoundException;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final ChatRepository chatRepository;
    private final SimpMessagingTemplate messagingTemplate;
    private final ChatService chatService;

    @Autowired
    private  MessageMapper messageMapper;

    @Override
    public ChatDto.MessageDto sendMessage(String chatId, ChatDto.MessageDto messageDto, String senderId) {
        try {
            // 1. Create object message
            User sender = userRepository.findById(senderId).orElseThrow(() -> new UserNotFoundException(senderId));
            Message message = messageMapper.messageDtoToMessage(messageDto);
            message.setSender(sender); // set sender
            message.setTimestamp(LocalDateTime.now()); // Thêm timestamp
            message.setChat(chatRepository.findById(chatId).orElseThrow(() -> new ChatNotFoundException(chatId)));
            message.setStatus(MessageStatus.SENT); // Thêm status
            // 2. Lưu tin nhắn vào database
            message = messageRepository.save(message);
            // 3. Lấy thông tin chat
            Chat chat = chatService.getChatById(chatId);
            // 4. Gửi tin nhắn qua WebSocket (hoặc cơ chế khác)
            String destination = chat.isGroupChat() ? "/topic/messages/" + chatId : "/queue/messages";
            ChatDto.MessageDto messageDtoResponse = messageMapper.messageToMessageDto(message);
            messagingTemplate.convertAndSend(destination, messageDtoResponse);

            return messageDtoResponse;
        } catch (ChatNotFoundException | UserNotFoundException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Error sending message", ex);
            throw new RuntimeException("Error sending message", ex);
        }
    }

    @Override
    public void markMessageAsRead(String chatId, String messageId, String id) {

    }

    @Override
    public MessageHeaders getMessageById(String messageId) {
        return null;
    }
}


