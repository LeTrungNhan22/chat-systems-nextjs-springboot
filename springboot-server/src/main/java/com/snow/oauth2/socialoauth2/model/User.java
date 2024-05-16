package com.snow.oauth2.socialoauth2.model;


import lombok.*;
import net.minidev.json.annotate.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Document(collection = "users")
public class User {
    @Id
    private String id;
    private String username;
    private String email;
    @JsonIgnore
    private String password = null;
    private ProviderType providerType;
    private String imageUrl;
    private String providerId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
