package com.snow.oauth2.socialoauth2.dto.request.chat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ImageChunkDto {
    private String chunk;
    private int index;
    private int totalChunks;
    private String messageId; // messageId để liên kết với tin nhắn
}