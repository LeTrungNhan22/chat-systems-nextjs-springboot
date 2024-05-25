package com.snow.oauth2.socialoauth2.controller;


import com.snow.oauth2.socialoauth2.dto.request.chat.ChatDto;
import com.snow.oauth2.socialoauth2.dto.request.chat.ChatMapper;
import com.snow.oauth2.socialoauth2.model.chat.Chat;
import com.snow.oauth2.socialoauth2.security.CurrentUser;
import com.snow.oauth2.socialoauth2.security.UserPrincipal;
import com.snow.oauth2.socialoauth2.service.chat.ChatService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/chats")
@RequiredArgsConstructor
@Slf4j
public class ChatController {

    private final ChatService chatService;

    @PostMapping("/{userId}/{friendId}")
    @Operation(summary = "Create chat")
    public ResponseEntity<ChatDto> createChat(@PathVariable String userId, @PathVariable String friendId) {
        Chat chat = chatService.createChat(userId, friendId);
        ChatDto chatDto = ChatMapper.INSTANCE.chatToChatDto(chat);
        return ResponseEntity.ok(chatDto);
    }

    @GetMapping("/{chatId}/sendMessage")
    public ResponseEntity<ChatDto.MessageDto> sendMessage2(
            @PathVariable String chatId,
            @RequestBody ChatDto.MessageDto messageDto,
            @CurrentUser UserPrincipal userPrincipal
    ) {
        return null;
    }


}
