package com.snow.oauth2.socialoauth2.repository;

import com.snow.oauth2.socialoauth2.model.chat.Chat;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ChatRepository extends MongoRepository<Chat, String> {

    List<Chat> findAllByParticipantsContaining(String userId);




}
