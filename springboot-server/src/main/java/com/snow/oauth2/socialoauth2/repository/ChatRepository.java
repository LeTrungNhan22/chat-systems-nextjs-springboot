package com.snow.oauth2.socialoauth2.repository;

import com.snow.oauth2.socialoauth2.model.chat.Chat;
import com.snow.oauth2.socialoauth2.model.user.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ChatRepository extends MongoRepository<Chat, String> {

    Optional<Object> findByParticipantsContaining(User user1);

}
