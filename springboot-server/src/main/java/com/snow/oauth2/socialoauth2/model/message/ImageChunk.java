package com.snow.oauth2.socialoauth2.model.message;


import lombok.Data;

@Data
public class ImageChunk {
    private String chunkData;
    private long chunkIndex;
    private long totalChunks;
}
