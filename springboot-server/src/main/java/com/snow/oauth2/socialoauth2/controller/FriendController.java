package com.snow.oauth2.socialoauth2.controller;


import com.snow.oauth2.socialoauth2.dto.response.FriendStatusUpdateResponseDto;
import com.snow.oauth2.socialoauth2.model.friend.FriendShip;
import com.snow.oauth2.socialoauth2.model.friend.FriendshipStatus;
import com.snow.oauth2.socialoauth2.service.friend.FriendService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/friends")
@RequiredArgsConstructor
public class FriendController {

    private final FriendService friendService;


    @GetMapping("/{userId}/requests/sent") // Lấy danh sách đã gửi
    @Operation(summary = "Get sent friend requests")
    public ResponseEntity<List<FriendShip>> getSentFriendRequests(@PathVariable String userId) {
        List<FriendShip> friendShipRequests = friendService.getFriendRequests(userId, true);
        return ResponseEntity.ok(friendShipRequests);
    }

    @GetMapping("/{userId}/requests/received") // Lấy danh sách đã nhận
    @Operation(summary = "Get received friend requests")
    public ResponseEntity<List<FriendShip>> getReceivedFriendRequests(@PathVariable String userId) {

        List<FriendShip> friendShipRequests = friendService.getFriendRequests(userId, false);
        return ResponseEntity.ok(friendShipRequests);
    }

    @PostMapping("/{userId}/requests/{friendId}")
    @Operation(summary = "Send friend request")
    public ResponseEntity<FriendShip> sendFriendRequest(@PathVariable String userId, @PathVariable String  friendId) {
        FriendShip createdFriendShipRequest = friendService.sendFriendRequest(userId, friendId);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFriendShipRequest);
    }

    @PutMapping("/{currentUserId}/requests/{friendRequestId}")
    @Operation(summary = "Update friend request")
    public ResponseEntity<FriendStatusUpdateResponseDto> updateFriendRequest(@PathVariable String currentUserId, @PathVariable String friendRequestId, @RequestParam("friend-status") FriendshipStatus friendStatus) {
        FriendStatusUpdateResponseDto updatedFriendRequest = friendService.updateFriendRequest(currentUserId, friendRequestId, friendStatus);
        return ResponseEntity.ok(updatedFriendRequest);
    }


    @GetMapping("/{userId}/friends")
    @Operation(summary = "Get list of friends")
    public ResponseEntity<List<FriendShip>> getListOfFriends(@PathVariable String userId) {
        List<FriendShip> friendShips = friendService.getListOfFriends(userId);
        return ResponseEntity.ok(friendShips);
    }


}
