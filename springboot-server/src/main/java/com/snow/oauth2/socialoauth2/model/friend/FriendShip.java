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
@Document(collection = "friendships")
@JsonIgnoreProperties(ignoreUnknown = true)
public class FriendShip {
    @Id
    private String id;
    private String userId1; // userId1 is the one who sends the request
    private String userId2; // userId2 is the one who receives the request
    private FriendshipStatus status;
    private LocalDateTime rejectedAt; // Thêm trường rejectedAt
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
