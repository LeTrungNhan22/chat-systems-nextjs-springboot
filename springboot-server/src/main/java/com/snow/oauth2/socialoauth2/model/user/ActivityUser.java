package com.snow.oauth2.socialoauth2.model.user;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "activity_users")
public class ActivityUser {
    @Id
    private String id;
    @DBRef
    private User user;
    private boolean isOnline;
    private LocalDateTime lastActiveAt;

}
