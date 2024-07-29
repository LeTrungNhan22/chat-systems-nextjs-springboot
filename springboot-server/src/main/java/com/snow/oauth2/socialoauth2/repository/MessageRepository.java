package com.snow.oauth2.socialoauth2.repository;

import com.snow.oauth2.socialoauth2.model.message.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MessageRepository extends MongoRepository<Message, String> {

    Page<Message> findByChatId(String chatId, Pageable pageable);
}
