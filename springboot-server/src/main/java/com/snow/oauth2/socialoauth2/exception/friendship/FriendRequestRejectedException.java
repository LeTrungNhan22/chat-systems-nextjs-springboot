package com.snow.oauth2.socialoauth2.exception.friendship;

import lombok.Getter;

@Getter
public class FriendRequestRejectedException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    // Getter để lấy giá trị retryAfterSeconds
    private long retryAfterSeconds;

    public FriendRequestRejectedException(String userId, String friendId) {
        super("Friend request rejected with userId: " + userId + " and friendId: " + friendId);
    }

    public FriendRequestRejectedException(String message, Throwable cause) {
        super(message, cause);
    }

    public FriendRequestRejectedException(String userId1, String userId2, long retryAfterSeconds) {
        super(String.format("Friend request from %s to %s was rejected. Please try again after %d seconds.",
                userId1, userId2, retryAfterSeconds));
        this.retryAfterSeconds = retryAfterSeconds;
    }

}
