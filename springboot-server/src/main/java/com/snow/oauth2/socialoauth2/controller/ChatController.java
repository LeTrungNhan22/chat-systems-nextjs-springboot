package com.snow.oauth2.socialoauth2.controller;


import com.snow.oauth2.socialoauth2.dto.request.chat.ChatDto;
import com.snow.oauth2.socialoauth2.dto.request.chat.ChatMapper;
import com.snow.oauth2.socialoauth2.dto.request.chat.MessageMapper;
import com.snow.oauth2.socialoauth2.exception.auth.ResourceNotFoundException;
import com.snow.oauth2.socialoauth2.model.chat.Chat;
import com.snow.oauth2.socialoauth2.model.chat.Message;
import com.snow.oauth2.socialoauth2.model.user.User;
import com.snow.oauth2.socialoauth2.repository.UserRepository;
import com.snow.oauth2.socialoauth2.security.CurrentUser;
import com.snow.oauth2.socialoauth2.security.UserPrincipal;
import com.snow.oauth2.socialoauth2.service.chat.ChatService;
import com.snow.oauth2.socialoauth2.service.message.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/chats")
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatService chatService;
    private final MessageService messageService;
    private final UserRepository userRepository;

    @PostMapping("/{userId}/{friendId}")
    @Operation(summary = "Create chat")
    public ResponseEntity<ChatDto> createChat(@PathVariable String userId, @PathVariable String friendId) {
        Chat chat = chatService.createChat(userId, friendId);
        ChatDto chatDto = ChatMapper.INSTANCE.chatToChatDto(chat);
        return ResponseEntity.ok(chatDto);
    }

    @PostMapping("/{chatId}/messages")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Send message")
    public ResponseEntity<ChatDto.MessageDto> sendMessage(
            @PathVariable String chatId,
            @RequestBody ChatDto.MessageDto messageDto,
            @CurrentUser UserPrincipal userPrincipal
    ) {
      User userDetails =  userRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
        String userId = userDetails.getId();
        messageDto.setSenderId(userId);

        Message message = messageService.sendMessage(chatId, messageDto);

        ChatDto.MessageDto response = MessageMapper.INSTANCE.messageToMessageDto(message);

        messagingTemplate.convertAndSend("/topic/chat/" + chatId, response);
        return ResponseEntity.ok(response);
    }


}
