package com.snow.oauth2.socialoauth2.controller;

import com.snow.oauth2.socialoauth2.dto.request.NotificationDto;
import com.snow.oauth2.socialoauth2.dto.request.chat.ChatDto;
import com.snow.oauth2.socialoauth2.dto.request.chat.MessageMapper;
import com.snow.oauth2.socialoauth2.dto.request.user.UserDto;
import com.snow.oauth2.socialoauth2.exception.notfoud.ChatNotFoundException;
import com.snow.oauth2.socialoauth2.exception.notfoud.UserNotFoundException;
import com.snow.oauth2.socialoauth2.model.chat.Chat;
import com.snow.oauth2.socialoauth2.model.chat.Message;
import com.snow.oauth2.socialoauth2.model.notifications.NotificationType;
import com.snow.oauth2.socialoauth2.security.CurrentUser;
import com.snow.oauth2.socialoauth2.security.UserPrincipal;
import com.snow.oauth2.socialoauth2.service.chat.ChatService;
import com.snow.oauth2.socialoauth2.service.message.MessageService;
import com.snow.oauth2.socialoauth2.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MessageController {

    private static final Logger logger = LoggerFactory.getLogger(ChatController.class);

    private final SimpMessagingTemplate messagingTemplate;
    private final MessageService messageService;
    private final UserService userService;
    private final ChatService chatService;

    @MessageMapping("/app/hello")
    @SendTo("/topic/greetings")
    public String greeting(String message) throws Exception {
        Thread.sleep(1000); // Simulated delay
        return "Hello, " + message + "!";
    }


//    @MessageMapping("/{chatId}/sendMessage")
//    @PreAuthorize("hasRole('USER')")
//    public ResponseEntity<ChatDto.MessageDto> sendMessage(
//            @PathVariable String chatId,
//            @RequestBody ChatDto.MessageDto messageDto,
//            @CurrentUser UserPrincipal userPrincipal
//    ) {
//        try {
//            Message message = messageService.sendMessage(chatId, messageDto, userPrincipal.getId());
//            ChatDto.MessageDto messageDtoResponse = MessageMapper.INSTANCE.messageToMessageDto(message);
//            Chat chat = chatService.getChatById(chatId);
//            String destination = chat.isGroupChat() ? "/topic/messages/" + chatId : "/queue/messages";
//            messagingTemplate.convertAndSend(destination, messageDtoResponse);
//            return ResponseEntity.ok(messageDtoResponse);
//        } catch (ChatNotFoundException | UserNotFoundException ex) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        } catch (Exception ex) {
//            logger.error("Error sending message: ", ex);
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//
//    }
//
//    @MessageMapping("/chat/{chatId}/forwardMessage")
//    @Operation(summary = "Forward message")
//    public void forwardMessage(@DestinationVariable String chatId, ChatDto.MessageDto messageDto) {
//        Chat chat = chatService.getChatById(chatId); // Lấy thông tin chat
//        String receiverId = chatService.getReceiverId(chat, messageDto.getSenderId()); // Lấy receiverId từ ChatService
//        messagingTemplate.convertAndSendToUser(receiverId, "/queue/messages", messageDto);
//    }
//
//
//    @MessageMapping("/chat/{chatId}/messages/{messageId}/read")
//    @PreAuthorize("hasRole('USER')")
//    public void markMessageAsRead(
//            @DestinationVariable String chatId,
//            @DestinationVariable String messageId,
//            @CurrentUser UserPrincipal userPrincipal
//    ) {
//        // Update message status to read
//        UserDto user = userService.getUserById(userPrincipal.getId());
//        // Call service to update message status
//        messageService.markMessageAsRead(chatId, messageId, user.getId());
//
//        // notify sender that message has been read
//        Chat chat = chatService.getChatById(chatId);
//        String senderId = chatService.getReceiverId(chat, user.getId());
//        NotificationDto notificationDto = new NotificationDto();
//        notificationDto.setType(NotificationType.MESSAGE);
//        notificationDto.setRead(true);
//        notificationDto.setRecipient(senderId);
//        notificationDto.setContent("Message has been read");
//        notificationDto.setCreatedAt(messageService.getMessageById(messageId).getTimestamp());
//        messagingTemplate.convertAndSendToUser(senderId, "/queue/notifications", notificationDto);
//
//
//    }

}
