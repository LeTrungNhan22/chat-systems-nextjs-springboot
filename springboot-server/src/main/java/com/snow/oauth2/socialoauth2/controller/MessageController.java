package com.snow.oauth2.socialoauth2.controller;

import com.snow.oauth2.socialoauth2.dto.request.chat.MessageRequestDto;
import com.snow.oauth2.socialoauth2.exception.message.MediaSizeLimitExceededException;
import com.snow.oauth2.socialoauth2.security.CurrentUser;
import com.snow.oauth2.socialoauth2.security.UserPrincipal;
import com.snow.oauth2.socialoauth2.service.message.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;

@RequiredArgsConstructor
@Slf4j
public class MessageController {

    private static final Logger logger = LoggerFactory.getLogger(ChatController.class);

    private final MessageService messageService;
    private final SimpMessagingTemplate messagingTemplate;


    @MessageMapping("/chats/{chatId}/sendMessage")
    @PreAuthorize("hasRole('USER')")
    public void sendMessage(
            @DestinationVariable String chatId,
            @Payload MessageRequestDto messageRequestDto,
            @CurrentUser UserPrincipal userPrincipal
    ) {
        try {
            messageService.sendMessage(chatId, messageRequestDto, userPrincipal.getId());
        } catch (MediaSizeLimitExceededException e) {
            messagingTemplate.convertAndSendToUser(userPrincipal.getUsername(), "/queue/errors", e.getMessage());
        } catch (Exception e) {
            messagingTemplate.convertAndSendToUser(userPrincipal.getUsername(), "/queue/errors", e.getMessage());
            logger.error("Error occurred while sending message", e);
        }
    }

}
