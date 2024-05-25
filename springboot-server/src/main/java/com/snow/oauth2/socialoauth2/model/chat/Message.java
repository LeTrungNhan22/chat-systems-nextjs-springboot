package com.snow.oauth2.socialoauth2.model.chat;


import com.snow.oauth2.socialoauth2.model.user.User;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "messages")
public class Message {
    @Id
    private String id;
    @DBRef
    private User sender;
    @DBRef
    private User receiver;
    private String messageContent;
    private LocalDateTime timestamp;
    private MessageStatus status;
    private MessageType messageType;
    @DBRef
    private Chat chat; // This is the chat to which the message belongs

}
