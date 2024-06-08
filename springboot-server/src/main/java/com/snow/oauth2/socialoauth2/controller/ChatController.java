package com.snow.oauth2.socialoauth2.controller;

import com.snow.oauth2.socialoauth2.dto.mapper.ChatMapper;
import com.snow.oauth2.socialoauth2.dto.request.chat.ChatDto;
import com.snow.oauth2.socialoauth2.model.chat.Chat;
import com.snow.oauth2.socialoauth2.service.chat.ChatService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/v1/conversations")
@RequiredArgsConstructor
@Slf4j
public class ChatController {

    private final ChatService chatService;

    @PostMapping("/{userId}/{friendId}")
    @Operation(summary = "Create chat")
    public ResponseEntity<ChatDto> createChat(@PathVariable String userId, @PathVariable String friendId) {
        Chat chat = chatService.findOrCreateChat(userId, friendId);
        ChatDto chatDto = ChatMapper.INSTANCE.chatToChatDto(chat);
        return ResponseEntity.ok(chatDto);
    }

    @GetMapping("{chatId}")
    @Operation(summary = "Get chat by id")
    public ResponseEntity<ChatDto> getChatById(@PathVariable String chatId) {
        Chat chat = chatService.getChatById(chatId);
        ChatDto chatDto = ChatMapper.INSTANCE.chatToChatDto(chat);
        return ResponseEntity.ok(chatDto);
    }



    @GetMapping("/user/{userId}")
    @Operation(summary = "Get list of chat by user id")
    public ResponseEntity<List<ChatDto>> getChatByUserId(@PathVariable String userId) {
        List<Chat> chats = chatService.getListConversationByUserId(userId);
        List<ChatDto> chatDtos = chats.stream().map(ChatMapper.INSTANCE::chatToChatDto).collect(Collectors.toList());
        return ResponseEntity.ok(chatDtos);
    }
}
