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
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {
    private final UserRepository userRepository;
    private final FriendRepository friendRepository;
    private final ChatRepository chatRepository;
    private final MongoTemplate mongoTemplate;

    @Override
    public Chat createChat(String userId, String friendId) {
        User user1 = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        User user2 = userRepository.findById(friendId)
                .orElseThrow(() -> new UserNotFoundException(friendId));

        if (user1.getId().equals(user2.getId())) {
            throw new BadRequestException("Cannot create chat with yourself");
        }

        if (!friendRepository.existsFriendship(userId, friendId)) {
            throw new BadRequestException("You are not friend with this user");
        }

        // Kiểm tra xem cuộc trò chuyện đã tồn tại chưa (có thể đã tạo trước đó)
        Query query = new Query();
        query.addCriteria(Criteria.where("participants").all(Arrays.asList(userId, friendId)));
        query.addCriteria(Criteria.where("isGroupChat").is(false)); // Chỉ tìm kiếm chat cá nhân

        Optional<Chat> existingChat = Optional.ofNullable(mongoTemplate.findOne(query, Chat.class));


        if (existingChat.isPresent()) {
            return existingChat.get(); // Trả về cuộc trò chuyện đã tồn tại
        }


//        new chat
        Chat newChat = new Chat();
        newChat.setParticipants(Arrays.asList(user1, user2));
        newChat.setGroupChat(false);
        newChat.setCreatedAt(LocalDateTime.now());
        newChat.setUpdatedAt(LocalDateTime.now());

        return chatRepository.save(newChat);
    }

    @Override
    public User getReceiver(String chatId, String senderId) {
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new ChatNotFoundException(chatId));

        return chat.getParticipants().stream()
                .filter(user -> !user.getId().equals(senderId))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Receiver not found in chat"));
    }

    @Override
    public Chat getChatById(String chatId) {
        return chatRepository.findById(chatId)
                .orElseThrow(() -> new ChatNotFoundException(chatId));
    }

    @Override
    public String getReceiverId(Chat chat, String senderId) {
        if (chat.isGroupChat()) {
            // Nếu là nhóm chat, không cần tìm receiverId
            return null;
        } else {
            // Nếu là chat riêng tư, tìm người nhận là người khác với người gửi
            return chat.getParticipants().stream()
                    .filter(user -> !user.getId().equals(senderId))
                    .findFirst()
                    .map(User::getId)
                    .orElseThrow(() -> new IllegalStateException("Receiver not found in chat"));
        }
    }


}
