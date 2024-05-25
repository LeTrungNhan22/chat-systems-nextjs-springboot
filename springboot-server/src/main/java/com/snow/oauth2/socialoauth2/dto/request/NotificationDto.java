package com.snow.oauth2.socialoauth2.dto.request;

import com.snow.oauth2.socialoauth2.model.notifications.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationDto {
    private String id;
    private String recipient;
    private NotificationType type; // Loại thông báo (ví dụ: FRIEND_REQUEST, MESSAGE, etc.)
    private String content; // Nội dung thông báo
    private boolean isRead; // Đánh dấu đã đọc hay chưa
    private Long createdAt;


}
