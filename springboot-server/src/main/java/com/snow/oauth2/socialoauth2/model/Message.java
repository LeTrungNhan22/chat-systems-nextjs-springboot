package com.snow.oauth2.socialoauth2.model;

import lombok.Data;

@Data
public class Message {
    private String message;

    public Message() {
    }

    public Message(String message) {
        this.message = message;
    }
}
