package com.snow.oauth2.socialoauth2.service.friend;

import com.snow.oauth2.socialoauth2.dto.response.FriendStatusUpdateResponseDto;
import com.snow.oauth2.socialoauth2.exception.auth.BadRequestException;
import com.snow.oauth2.socialoauth2.exception.friendship.FriendRequestRejectedException;
import com.snow.oauth2.socialoauth2.exception.notfoud.InvalidUserException;
import com.snow.oauth2.socialoauth2.exception.notfoud.UserNotFoundException;
import com.snow.oauth2.socialoauth2.model.friend.FriendShip;
import com.snow.oauth2.socialoauth2.model.friend.FriendshipStatus;
import com.snow.oauth2.socialoauth2.model.user.User;
import com.snow.oauth2.socialoauth2.repository.FriendRepository;
import com.snow.oauth2.socialoauth2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class FriendServiceImpl implements FriendService {

    private final FriendRepository friendRepository;
    private final UserRepository userRepository;

    public FriendShip sendFriendRequest(String userId1, String userId2) {
        User user1 = userRepository.findById(userId1)
                .orElseThrow(() -> new UserNotFoundException(userId1));
        User user2 = userRepository.findById(userId2)
                .orElseThrow(() -> new UserNotFoundException(userId2));


        FriendShip existingFriendship = friendRepository.findByUserId1AndUserId2OrUserId1AndUserId2AndStatus(
                userId1, userId2, userId2, userId1, FriendshipStatus.PENDING
        );

        if (existingFriendship != null) {
            throw new BadRequestException("A friend request already exists between these users.");
        }

        FriendShip existingFriendshipRejected = validateRequestFriendShip(userId1, userId2);


        if (existingFriendshipRejected != null) { // Nếu existingFriendshipRejected không null, tức là đã có lời mời bị từ chối và được cập nhật
            return existingFriendshipRejected;
        }

        FriendShip friendShipRequest = FriendShip.builder()
                .userId1(user1.getId())
                .userId2(user2.getId())
                .status(FriendshipStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .updatedAt(null)
                .build();
        friendRepository.save(friendShipRequest);

        // Gửi thông báo cho người dùng được mời (tùy chọn, sử dụng WebSocket )
        return friendShipRequest;
    }


    @Override
    public List<FriendShip> getFriendRequests(String userId, boolean isSent) { // Thêm tham số isSent để xác định lấy danh sách gửi đi hay nhận về
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        List<FriendShip> friendShipRequests;
        if (isSent) {
            friendShipRequests = friendRepository.findByUserId1AndStatus(user.getId(), FriendshipStatus.PENDING);
        } else {
            friendShipRequests = friendRepository.findByUserId2AndStatus(user.getId(), FriendshipStatus.PENDING);
        }

        return friendShipRequests;
    }

    @Override
    public FriendStatusUpdateResponseDto updateFriendRequest(String userId2, String userId1, FriendshipStatus friendStatus) {
        User user = userRepository.findById(userId2)
                .orElseThrow(() -> new UserNotFoundException(userId2));

        FriendShip friendShip = friendRepository.findByUserId1AndUserId2(userId1, userId2);

        if (friendShip == null) {
            throw new UserNotFoundException(userId1);
        }

        if (!friendShip.getUserId2().equals(user.getId())) {
            throw new InvalidUserException("You are not authorized to update this friend request.");
        }

        friendShip.setStatus(friendStatus);
        friendShip.setUpdatedAt(LocalDateTime.now());
        if (friendStatus == FriendshipStatus.REJECTED) {
            friendShip.setRejectedAt(LocalDateTime.now()); // Cập nhật rejectedAt khi từ chối
        }
        friendRepository.save(friendShip);

        return FriendStatusUpdateResponseDto.builder()
                .userId1(friendShip.getUserId1())
                .userId2(friendShip.getUserId2())
                .status(friendShip.getStatus())
                .createdAt(friendShip.getCreatedAt())
                .updatedAt(friendShip.getUpdatedAt())
                .build();
    }


    @Override
    public List<FriendShip> getListOfFriends(String userId) {
        return friendRepository.getListOfFriends(userId);
    }


    //validate
    private FriendShip validateRequestFriendShip(String userId1, String userId2) {
        if (userId1.equals(userId2)) {
            throw new BadRequestException("User id1 and user id2 must be different");
        }
        // Kiểm tra xem có lời mời kết bạn trước đó với trạng thái REJECTED không
        FriendShip rejectedFriendship = friendRepository.findByUserId1AndUserId2AndStatus(
                userId1, userId2, FriendshipStatus.REJECTED
        );
        if (rejectedFriendship != null) {
            validateTimeReject(userId1, userId2, rejectedFriendship);
            return rejectedFriendship; // Trả về rejectedFriendship nếu đã cập nhật
        }

        rejectedFriendship = friendRepository.findByUserId1AndUserId2AndStatus(
                userId2, userId1, FriendshipStatus.REJECTED
        );
        if (rejectedFriendship != null) {
            validateTimeReject(userId2, userId1, rejectedFriendship);
            return rejectedFriendship; // Trả về rejectedFriendship nếu đã cập nhật
        }

        return null;
    }

    private void validateTimeReject(String userId1, String userId2, FriendShip rejectedFriendship) {
        if (rejectedFriendship != null) {
            LocalDateTime now = LocalDateTime.now();
            long minutesPassed = ChronoUnit.MINUTES.between(rejectedFriendship.getRejectedAt(), now);
            if (minutesPassed >= 3) { // Nếu đã đủ 3 phút
                rejectedFriendship.setStatus(FriendshipStatus.PENDING); // Cập nhật trạng thái
                rejectedFriendship.setRejectedAt(null); // Xóa rejectedAt
                friendRepository.save(rejectedFriendship); // Lưu lại
                return; // Thoát khỏi hàm, không cần kiểm tra thêm
            } else {
                long retryAfterSeconds = 180 - minutesPassed * 60;
                throw new FriendRequestRejectedException(userId1, userId2, retryAfterSeconds);
            }
        }

    }


}


