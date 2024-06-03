package com.snow.oauth2.socialoauth2.service.friend;

import com.snow.oauth2.socialoauth2.dto.request.friend.FriendDto;
import com.snow.oauth2.socialoauth2.dto.request.friend.FriendRequestDto;
import com.snow.oauth2.socialoauth2.dto.response.FriendStatusUpdateResponseDto;
import com.snow.oauth2.socialoauth2.exception.notfoud.FriendRequestAlreadyExistsException;
import com.snow.oauth2.socialoauth2.exception.notfoud.UserNotFoundException;
import com.snow.oauth2.socialoauth2.model.friend.Friend;
import com.snow.oauth2.socialoauth2.model.friend.FriendStatus;
import com.snow.oauth2.socialoauth2.model.user.User;
import com.snow.oauth2.socialoauth2.repository.FriendRepository;
import com.snow.oauth2.socialoauth2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
public class FriendServiceImpl implements FriendService {

    private final FriendRepository friendRepository;

    private final UserRepository userRepository;

    public Friend sendFriendRequest(String currentUserId, String friendId) {

        User currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new UserNotFoundException(currentUserId));
        User requestedUser = userRepository.findById(friendId)
                .orElseThrow(() -> new UserNotFoundException(friendId));

        if (friendRepository.existsByCurrentUserIdAndRequestFriendId(currentUser.getId(), requestedUser.getId()) ||
            friendRepository.existsByCurrentUserIdAndRequestFriendId(requestedUser.getId(), currentUser.getId())) {
            throw new FriendRequestAlreadyExistsException(currentUserId, friendId);
        }


        Friend friendRequest = Friend.builder()
                .currentUserId(currentUser.getId())
                .requestFriendId(requestedUser.getId())
                .status(FriendStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .updatedAt(null)
                .build();
        friendRepository.save(friendRequest);

        // 4. Gửi thông báo cho người dùng được mời (tùy chọn, sử dụng WebSocket )
        return friendRequest;
    }

    @Override
    public List<Friend> getFriendRequests(String userId, boolean isSent) { // Thêm tham số isSent để xác định lấy danh sách gửi đi hay nhận về
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        List<Friend> friendRequests;
        if (isSent) {
            friendRequests = friendRepository.findByCurrentUserIdAndStatus(user.getId(), FriendStatus.PENDING);
        } else {
            friendRequests = friendRepository.findByRequestFriendIdAndStatus(user.getId(), FriendStatus.PENDING);
        }

        return friendRequests;
    }

    @Override
    public FriendStatusUpdateResponseDto updateFriendRequest(String currentUserId, String friendRequestId, FriendStatus friendStatus) {
        User user = userRepository.findById(friendRequestId)
                .orElseThrow(() -> new UserNotFoundException(friendRequestId));
        Friend friend = friendRepository.findByRequestFriendId(currentUserId);
        if (friend == null) {
            throw new UserNotFoundException(currentUserId);
        }

        if (!friend.getCurrentUserId().equals(user.getId())) {
            throw new UserNotFoundException(currentUserId);
        }

        friend.setStatus(friendStatus);
        friendRepository.save(friend);

        return FriendStatusUpdateResponseDto.builder()
                .currentUserId(friend.getCurrentUserId())
                .requestFriendId(friend.getRequestFriendId())
                .status(friend.getStatus())
                .createdAt(friend.getCreatedAt())
                .updatedAt(LocalDateTime.now()).build();
    }

    @Override
    public List<Friend> getListOfFriends(String userId) {
        return friendRepository.getListOfFriends(userId);
    }

    // Hàm ánh xạ từ Friend sang FriendDto
    private FriendDto mapToFriendDto(Friend friend) {
        return FriendDto.builder()
                .senderId(friend.getCurrentUserId())
                .receiverId(friend.getRequestFriendId())
                .status(String.valueOf(friend.getStatus()))
                .build();
    }
}


