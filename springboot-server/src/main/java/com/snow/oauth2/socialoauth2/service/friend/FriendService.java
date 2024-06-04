package com.snow.oauth2.socialoauth2.service.friend;

import com.snow.oauth2.socialoauth2.dto.response.FriendStatusUpdateResponseDto;
import com.snow.oauth2.socialoauth2.model.friend.FriendShip;
import com.snow.oauth2.socialoauth2.model.friend.FriendshipStatus;

import java.util.List;

public interface FriendService {
    FriendShip sendFriendRequest(String userId1, String userId2);

    List<FriendShip> getFriendRequests(String userId, boolean isSent);

    FriendStatusUpdateResponseDto updateFriendRequest(String currentUserId, String friendRequestId, FriendshipStatus friendStatus);

    List<FriendShip> getListOfFriends(String userId);
}



