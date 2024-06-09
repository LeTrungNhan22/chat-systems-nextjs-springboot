package com.snow.oauth2.socialoauth2.dto.request.chat;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.snow.oauth2.socialoauth2.model.chat.MessageType;
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
    @JsonProperty("mediaBase64")
    private String mediaBase64;
    @JsonProperty("keywords")
    private List<String> keywords;
}
