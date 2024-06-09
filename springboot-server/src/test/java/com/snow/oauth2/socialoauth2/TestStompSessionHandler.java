package com.snow.oauth2.socialoauth2;

import com.snow.oauth2.socialoauth2.dto.request.chat.MessageRequestDto;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class TestStompSessionHandler  extends StompSessionHandlerAdapter {
    private final BlockingQueue<MessageRequestDto> blockingQueue = new LinkedBlockingQueue<>(); // Thay đổi kiểu BlockingQueue

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        blockingQueue.offer((MessageRequestDto) payload); // Chuyển đổi payload thành MessageRequestDto
    }

    public MessageRequestDto getPayload() throws InterruptedException { // Thay đổi kiểu trả về
        return blockingQueue.poll(1, TimeUnit.SECONDS);
    }
}
