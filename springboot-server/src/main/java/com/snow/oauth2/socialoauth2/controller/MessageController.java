package com.snow.oauth2.socialoauth2.controller;

import com.snow.oauth2.socialoauth2.dto.request.chat.MessageRequestDto;
import com.snow.oauth2.socialoauth2.exception.message.MediaSizeLimitExceededException;
import com.snow.oauth2.socialoauth2.service.message.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;


@Controller
@RequiredArgsConstructor
@Slf4j
public class MessageController {

    private static final Logger logger = LoggerFactory.getLogger(ChatController.class);

    private final MessageService messageService;
    private final SimpMessagingTemplate messagingTemplate;


    @MessageMapping("/chats/{chatId}/sendMessage")
    public void sendMessage(
            @DestinationVariable String chatId,
            @Payload MessageRequestDto messageRequestDto,
            @Header("Authorization") String authorizationHeader
    ) {
        try {
            logger.info("Received message request for chat ID: {}", chatId);
            logger.info("Message content: {}", messageRequestDto.getContent());
            // 2. Gọi service để gửi tin nhắn (truyền userId)
            messageService.sendMessage(chatId, messageRequestDto, authorizationHeader);
        } catch (MediaSizeLimitExceededException e) {
            messagingTemplate.convertAndSend("/topic/errors", e.getMessage());
            logger.error("Error occurred while sending message", e);
        } catch (Exception e) {
            messagingTemplate.convertAndSend("/topic/errors", "An error occurred while sending message");
            logger.error("Error occurred while sending message", e);

        }
    }

}
