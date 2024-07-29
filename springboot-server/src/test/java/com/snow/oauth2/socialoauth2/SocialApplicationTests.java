package com.snow.oauth2.socialoauth2;

import com.snow.oauth2.socialoauth2.dto.request.chat.MessageRequestDto;
import com.snow.oauth2.socialoauth2.model.message.MessageType;
import com.snow.oauth2.socialoauth2.service.message.MessageService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.util.List;
import java.util.concurrent.TimeUnit;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@WithMockUser(roles = "USER")
class SocialApplicationTests {
    @LocalServerPort
    private int port;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MockBean
    private MessageService messageService;

    @Test
    public void testSendMessage_success() throws Exception {
        WebSocketStompClient stompClient = new WebSocketStompClient(new SockJsClient(
                List.of(new WebSocketTransport(new StandardWebSocketClient()))));

        StompSession stompSession = stompClient
                .connect("ws://localhost:" + port + "/ws", new StompSessionHandlerAdapter() {
                })
                .get(1, TimeUnit.SECONDS);

        MessageRequestDto messageRequestDto = new MessageRequestDto();
        messageRequestDto.setChatId("123");
        messageRequestDto.setContent("Hello");
        messageRequestDto.setMessageType(MessageType.TEXT);
        messageRequestDto.setMediaUrl(null);
        messageRequestDto.setKeywords(List.of("hello"));


        TestStompSessionHandler handler = new TestStompSessionHandler();
        stompSession.subscribe("/topic/chats/123", handler);


        // Gửi tin nhắn (không cần chuyển đổi thành JSON)
        stompSession.send("/app/chats/123/sendMessage", messageRequestDto);

// Chờ và lấy tin nhắn từ topic
        MessageRequestDto receivedMessage = handler.getPayload(); // Thay đổi kiểu receivedMessage
        Assertions.assertNotNull(receivedMessage);
        Assertions.assertEquals(messageRequestDto, receivedMessage);

        // Kiểm tra nội dung của receivedMessage
        // Xác minh rằng messageService.sendMessage() được gọi
        ArgumentCaptor<String> chatIdCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<MessageRequestDto> messageDtoCaptor = ArgumentCaptor.forClass(MessageRequestDto.class);
        ArgumentCaptor<String> userIdCaptor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(messageService).sendMessage(chatIdCaptor.capture(), messageDtoCaptor.capture(), userIdCaptor.capture());

        // Kiểm tra các tham số được truyền vào messageService.sendMessage()
        Assertions.assertEquals("123", chatIdCaptor.getValue());
        Assertions.assertEquals(messageRequestDto, messageDtoCaptor.getValue());
        Assertions.assertEquals("user", userIdCaptor.getValue()); // Giả sử userPrincipal.getId() trả về "user"

    }


}
