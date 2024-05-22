package com.snow.oauth2.socialoauth2.repository;

import com.snow.oauth2.socialoauth2.model.friend.Friend;
import com.snow.oauth2.socialoauth2.model.friend.FriendStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface FriendRepository extends MongoRepository<Friend, String> {
    boolean existsByCurrentUserIdAndRequestFriendId(String currentUserId, String requestFriendId);

    List<Friend> findByCurrentUserIdAndStatus(String user, FriendStatus friendStatus);

    List<Friend> findByRequestFriendIdAndStatus(String id, FriendStatus friendStatus);

    Friend findByRequestFriendId(String friendRequestId);

    @Query(value = "{$or: [{currentUserId: ?0, requestFriendId: ?1, status: 'ACCEPTED'}, {currentUserId: ?1, requestFriendId: ?0, status: 'ACCEPTED'}]}", exists = true)
    boolean existsFriendship(String currentUserId, String requestFriendId);

    @Query("{ $or: [ { currentUserId: ?0 }, { requestFriendId: ?0 } ], status: 'ACCEPTED' }")
    List<Friend> getListOfFriends(String userId);


}
