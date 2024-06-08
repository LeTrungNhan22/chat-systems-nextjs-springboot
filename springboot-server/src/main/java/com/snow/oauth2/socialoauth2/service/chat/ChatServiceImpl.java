package com.snow.oauth2.socialoauth2.service.chat;

import com.snow.oauth2.socialoauth2.exception.auth.BadRequestException;
import com.snow.oauth2.socialoauth2.exception.notfoud.ChatNotFoundException;
import com.snow.oauth2.socialoauth2.exception.notfoud.UserNotFoundException;
import com.snow.oauth2.socialoauth2.model.chat.Chat;
import com.snow.oauth2.socialoauth2.model.user.User;
import com.snow.oauth2.socialoauth2.repository.ChatRepository;
import com.snow.oauth2.socialoauth2.repository.FriendRepository;
import com.snow.oauth2.socialoauth2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {
    private final UserRepository userRepository;
    private final FriendRepository friendRepository;
    private final ChatRepository chatRepository;
    private final MongoTemplate mongoTemplate;

    private static final boolean NOT_GROUP_CHAT = false;

    @Override
    public Chat findOrCreateChat(String userId, String friendId) { // Đổi tên hàm
        User user1 = getUserByIdOrThrow(userId);
        User user2 = getUserByIdOrThrow(friendId);

        validateFriendship(user1, user2);

        return findExistingPersonalChat(userId, friendId)
                .orElseGet(() -> createNewPersonalChat(user1, user2));
    }
    private User getUserByIdOrThrow(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }
    private void validateFriendship(User user1, User user2){
        if (user1.getId().equals(user2.getId())) {
            throw new BadRequestException("Cannot create chat with yourself");
        }

        if (!friendRepository.existsFriendship(user1.getId(), user2.getId())) {
            throw new BadRequestException("You are not friend with this user");
        }

    }
    private Optional<Chat> findExistingPersonalChat(String userId, String friendId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("participants").all(Arrays.asList(userId, friendId)));
        query.addCriteria(Criteria.where("isGroupChat").is(NOT_GROUP_CHAT)); // Sử dụng hằng số
        return Optional.ofNullable(mongoTemplate.findOne(query, Chat.class));
    }

    private Chat createNewPersonalChat(User user1, User user2) {
        Chat newChat = new Chat();
        newChat.setParticipants(Arrays.asList(user1, user2));
        newChat.setGroupChat(NOT_GROUP_CHAT);
        newChat.setCreatedAt(LocalDateTime.now());
        return chatRepository.save(newChat);
    }


    @Override
    public Chat getChatById(String chatId) {
        return chatRepository.findById(chatId)
                .orElseThrow(() -> new ChatNotFoundException(chatId));
    }

    @Override
    public List<Chat> getListConversationByUserId(String userId) {
        return chatRepository.findAllByParticipantsContaining(userId);
    }

//    clean code up



}
