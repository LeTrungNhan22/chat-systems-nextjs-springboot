package com.snow.oauth2.socialoauth2.model.user;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "users")
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    private String id;
    private String username;
    @Indexed(unique = true)
    private String email;
    @JsonIgnore
    private String password = null;
    private ProviderType providerType;
    private String imageUrl;
    private String providerId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
