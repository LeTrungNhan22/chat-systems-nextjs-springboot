package com.snow.oauth2.socialoauth2.repository;

import com.snow.oauth2.socialoauth2.model.friend.FriendShip;
import com.snow.oauth2.socialoauth2.model.friend.FriendshipStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface FriendRepository extends MongoRepository<FriendShip, String> {

    @Query(value = "{$or: [{userId1: ?0, userId2: ?1, status: 'ACCEPTED'}, {userId1: ?1, userId2: ?0, status: 'ACCEPTED'}]}", exists = true)
    boolean existsFriendship(String currentUserId, String requestFriendId);

    @Query("{ $or: [ { userId1: ?0 }, { userId2: ?0 } ], status: 'ACCEPTED' }")
    List<FriendShip> getListOfFriends(String userId);

    FriendShip findByUserId1AndUserId2(String userId1, String userId2);

    List<FriendShip> findByUserId1AndStatus(String id, FriendshipStatus friendshipStatus);

    List<FriendShip> findByUserId2AndStatus(String id, FriendshipStatus friendshipStatus);

    FriendShip findByUserId1AndUserId2AndStatus(String userId1, String userId2, FriendshipStatus friendshipStatus);

    FriendShip findByUserId1AndUserId2OrUserId1AndUserId2AndStatus(String userId1, String userId2, String userId21, String userId11, FriendshipStatus friendshipStatus);
}
