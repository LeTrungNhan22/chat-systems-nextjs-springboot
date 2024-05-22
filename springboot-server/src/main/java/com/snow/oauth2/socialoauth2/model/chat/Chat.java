package com.snow.oauth2.socialoauth2.model.chat;


import com.snow.oauth2.socialoauth2.model.user.User;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@Document(collection = "chats")
public class Chat {

    @Id
    private String id;
    @DBRef(lazy = true)
    private List<User> participants;
    private boolean isGroupChat;
    private String groupName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @DBRef
    private Message lastMessage;
    private Map<String, Integer> unreadMessagesCount;
    @DBRef
    private User admin; // Chỉ có ý nghĩa khi isGroupChat = true
    private String avatar; // Chỉ có ý nghĩa khi isGroupChat = true

}
