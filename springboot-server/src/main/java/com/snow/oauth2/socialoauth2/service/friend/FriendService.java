package com.snow.oauth2.socialoauth2.service.friend;

import com.snow.oauth2.socialoauth2.dto.request.friend.FriendDto;
import com.snow.oauth2.socialoauth2.dto.request.friend.FriendRequestDto;
import com.snow.oauth2.socialoauth2.dto.response.FriendStatusUpdateResponseDto;
import com.snow.oauth2.socialoauth2.model.friend.Friend;
import com.snow.oauth2.socialoauth2.model.friend.FriendStatus;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface FriendService {
    FriendRequestDto sendFriendRequest(String currentUserId, FriendRequestDto friendRequestDto);

    List<FriendDto> getFriendRequests(String userId, boolean isSent);

    FriendStatusUpdateResponseDto updateFriendRequest(String userId, String friendRequestId, FriendStatus friendStatus);

    List<Friend> getListOfFriends(String userId);
}



