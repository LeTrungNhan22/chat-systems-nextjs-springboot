package com.snow.oauth2.socialoauth2.controller;

import com.snow.oauth2.socialoauth2.dto.request.chat.ChatDto;
import com.snow.oauth2.socialoauth2.exception.notfoud.ChatNotFoundException;
import com.snow.oauth2.socialoauth2.exception.notfoud.UserNotFoundException;
import com.snow.oauth2.socialoauth2.security.CurrentUser;
import com.snow.oauth2.socialoauth2.security.UserPrincipal;
import com.snow.oauth2.socialoauth2.service.message.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MessageController {

    private static final Logger logger = LoggerFactory.getLogger(ChatController.class);

    private final MessageService messageService;


    @MessageMapping("/{chatId}/sendMessage")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ChatDto.MessageDto> sendMessage(
            @PathVariable String chatId,
            @RequestBody ChatDto.MessageDto messageDto,
            @CurrentUser UserPrincipal userPrincipal
    ) {
        try {
            ChatDto.MessageDto messageDtoResponse = messageService.sendMessage(chatId, messageDto, userPrincipal.getId());
            return ResponseEntity.ok(messageDtoResponse);
        } catch (ChatNotFoundException | UserNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            logger.error("Error sending message: ", ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
