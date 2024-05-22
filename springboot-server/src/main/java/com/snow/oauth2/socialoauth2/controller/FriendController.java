package com.snow.oauth2.socialoauth2.controller;


import com.snow.oauth2.socialoauth2.dto.request.friend.FriendDto;
import com.snow.oauth2.socialoauth2.dto.request.friend.FriendRequestDto;
import com.snow.oauth2.socialoauth2.dto.response.FriendStatusUpdateResponseDto;
import com.snow.oauth2.socialoauth2.model.friend.Friend;
import com.snow.oauth2.socialoauth2.model.friend.FriendStatus;
import com.snow.oauth2.socialoauth2.service.friend.FriendService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/friends")
@RequiredArgsConstructor
public class FriendController {

    private final FriendService friendService;


    @GetMapping("/{userId}/requests/sent") // Lấy danh sách đã gửi
    @Operation(summary = "Get sent friend requests")
    public ResponseEntity<List<FriendDto>> getSentFriendRequests(@PathVariable String userId) {
        List<FriendDto> friendRequests = friendService.getFriendRequests(userId, true);
        return ResponseEntity.ok(friendRequests);
    }

    @GetMapping("/{userId}/requests/received") // Lấy danh sách đã nhận
    @Operation(summary = "Get received friend requests")
    public ResponseEntity<List<FriendDto>> getReceivedFriendRequests(@PathVariable String userId) {
        List<FriendDto> friendRequests = friendService.getFriendRequests(userId, false);
        return ResponseEntity.ok(friendRequests);
    }

    @PostMapping("/{userId}/requests")
    @Operation(summary = "Send friend request")
    public ResponseEntity<FriendRequestDto> sendFriendRequest(@PathVariable String userId, @RequestBody FriendRequestDto friendRequestDto) {
        FriendRequestDto createdFriendRequest = friendService.sendFriendRequest(userId, friendRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFriendRequest);
    }

    @PutMapping("/{currentUserId}/requests/{friendRequestId}")
    @Operation(summary = "Update friend request")
    public ResponseEntity<FriendStatusUpdateResponseDto> updateFriendRequest(@PathVariable String currentUserId, @PathVariable String friendRequestId, @RequestParam("friend-status") FriendStatus friendStatus) {
        FriendStatusUpdateResponseDto updatedFriendRequest = friendService.updateFriendRequest(currentUserId, friendRequestId, friendStatus);
        return ResponseEntity.ok(updatedFriendRequest);
    }


    @GetMapping("/{userId}/friends")
    @Operation(summary = "Get list of friends")
    public ResponseEntity<List<Friend>> getListOfFriends(@PathVariable String userId) {
        List<Friend> friends = friendService.getListOfFriends(userId);
        return ResponseEntity.ok(friends);
    }


}
