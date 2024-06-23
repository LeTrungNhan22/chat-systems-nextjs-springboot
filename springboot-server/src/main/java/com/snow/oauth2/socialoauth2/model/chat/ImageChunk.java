package com.snow.oauth2.socialoauth2.model.chat;


import lombok.Data;

@Data
public class ImageChunk {
    private String chunkData;
    private long chunkIndex;
    private long totalChunks;
}
