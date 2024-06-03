package com.snow.oauth2.socialoauth2.model.friend;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "friends")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Friend {
    @Id
    private String id;
    private String currentUserId;
    private String requestFriendId;
    private FriendStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
