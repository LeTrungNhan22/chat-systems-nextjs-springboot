package com.snow.oauth2.socialoauth2.dto.request.chat;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.snow.oauth2.socialoauth2.model.message.MessageType;
import lombok.Data;

import java.util.List;

@Data
public class MessageRequestDto {
    @JsonProperty("chatId")
    private String chatId;
    @JsonProperty("content")// ID của cuộc trò chuyện
    private String content;
    @JsonProperty("messageType")
    private MessageType messageType;
    @JsonProperty("mediaUrl")
    private List<String> mediaUrl;
    @JsonProperty("chunkIndex")
    private int chunkIndex;
    @JsonProperty("totalChunks")
    private int totalChunks;
    @JsonProperty("keywords")
    private List<String> keywords;
}
