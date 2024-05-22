package com.snow.oauth2.socialoauth2.exception.friend;

public class FriendRequestAlreadyExistsException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public FriendRequestAlreadyExistsException(String userId, String friendId) {
        super("Friend request already exists with userId: " + userId + " and friendId: " + friendId);
    }

    public FriendRequestAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
