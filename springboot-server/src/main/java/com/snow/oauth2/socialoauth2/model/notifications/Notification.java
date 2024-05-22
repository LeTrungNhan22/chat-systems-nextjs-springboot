package com.snow.oauth2.socialoauth2.model.notifications;

import com.snow.oauth2.socialoauth2.model.user.User;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;


@Data
@Document(collection = "notifications")
public class Notification {
    @Id
    private String id;
    @DBRef
    private User recipient;
    private NotificationType type; // Loại thông báo (ví dụ: FRIEND_REQUEST, MESSAGE, etc.)
    private String content; // Nội dung thông báo
    private boolean isRead; // Đánh dấu đã đọc hay chưa
    private LocalDateTime createdAt;

}