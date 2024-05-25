package com.snow.oauth2.socialoauth2.exception.notfoud;

public class ChatNotFoundException extends RuntimeException {

        private static final long serialVersionUID = 1L;

        public ChatNotFoundException(String chatId) {
            super("Chat not found with id: " + chatId);
        }

        public ChatNotFoundException(String message, Throwable cause) {
            super(message, cause);
        }
    }
