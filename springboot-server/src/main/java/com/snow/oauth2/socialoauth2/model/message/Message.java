package com.snow.oauth2.socialoauth2.model.message;


import com.snow.oauth2.socialoauth2.model.chat.Chat;
import com.snow.oauth2.socialoauth2.model.user.User;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "messages")
public class Message {
    @Id
    private String id;
    @DBRef
    private User sender;
    private String messageContent;
    private List<String> mediaUrl;
    private long timestamp;
    private MessageStatus status;
    private MessageType messageType;
    @DBRef
    private Chat chat;
    private List<String> keywords;  // Trường mới để lưu trữ các từ khóa quan trọng (để tìm kiếm nhanh hơn)
    private List<Emoji> emojis;
    private boolean isEdited;
    private boolean isSaved;

}
