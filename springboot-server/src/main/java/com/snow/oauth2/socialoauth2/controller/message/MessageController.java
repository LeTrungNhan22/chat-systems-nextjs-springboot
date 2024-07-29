package com.snow.oauth2.socialoauth2.controller.message;

import com.snow.oauth2.socialoauth2.model.page.PagedResponse;
import com.snow.oauth2.socialoauth2.model.message.Message;
import com.snow.oauth2.socialoauth2.service.message.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @GetMapping
    @Operation(summary = "Get all messages")
    public PagedResponse<Message> getAllMessages(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "timestamp") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Message> messagePage = messageService.findAll(pageable);
        return PagedResponse.PageConverter.toCustomPage(messagePage); // Chuyển đổi thành PagedResponse

    }

    @GetMapping("/by-chat/{chatId}")
    @Operation(summary = "Get messages by chat id")
    public ResponseEntity<PagedResponse<Message>> getMessagesByChatId(
            @PathVariable String chatId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "timestamp") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Message> messagePage = messageService.findByChatId(chatId, pageable); // Tìm tin nhắn theo chatId
        return ResponseEntity.ok(PagedResponse.PageConverter.toCustomPage(messagePage)); // Chuyển đổi thành PagedResponse
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get message by id")
    public ResponseEntity<Message> getMessageById(@PathVariable String id) {
        return ResponseEntity.ok(messageService.findById(id)); // Tìm tin nhắn theo id
    }

}