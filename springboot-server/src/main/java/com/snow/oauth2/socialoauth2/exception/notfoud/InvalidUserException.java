package com.snow.oauth2.socialoauth2.exception.notfoud;

public class InvalidUserException   extends RuntimeException {
    public InvalidUserException(String message) {
        super("Invalid User: " + message);
    }

    public InvalidUserException(String message, Throwable cause) {
        super(message, cause);
    }
}
