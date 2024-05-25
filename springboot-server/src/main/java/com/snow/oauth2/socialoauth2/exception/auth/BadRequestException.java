package com.snow.oauth2.socialoauth2.exception.auth;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super("Bad Request: " + message);
    }

    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
