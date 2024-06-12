package com.snow.oauth2.socialoauth2.configuration.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.snow.oauth2.socialoauth2.dto.request.chat.MessageRequestDto;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.client.standard.WebSocketContainerFactoryBean;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.List;
import java.util.Map;

@Configuration
@EnableWebSocketMessageBroker
@Order(Ordered.HIGHEST_PRECEDENCE + 1)
public class WebsocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry
                .addEndpoint("/ws") // Your WebSocket endpoint
                .setAllowedOriginPatterns("*")  // Allow all origins (adjust for production)
                .withSockJS()
                .setTaskScheduler(new ConcurrentTaskScheduler())
                .setHeartbeatTime(30000)
                .setInterceptors(new HandshakeInterceptor() {

                    @Override
                    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {

                        if (request instanceof org.springframework.http.server.ServletServerHttpRequest) {
                            org.springframework.http.server.ServletServerHttpRequest servletRequest = (org.springframework.http.server.ServletServerHttpRequest) request;
                            org.springframework.http.HttpHeaders headers = servletRequest.getHeaders();
                            String originHeader = headers.getOrigin();

                            if (originHeader != null && !originHeader.isEmpty()) {
                                response.getHeaders().add("Access-Control-Allow-Origin", originHeader);
                                response.getHeaders().add("Access-Control-Allow-Credentials", "true");
                                response.getHeaders().add("Authorization", headers.getFirst("Authorization"));
                            }
                        }

                        // Add the "server" header here
                        response.getHeaders().add("server", "WebSocket Chatterbox Server");
                        return true;
                    }

                    @Override
                    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
                        // Do nothing
                        if (exception != null) {
                            LoggerFactory.getLogger(WebsocketConfig.class).error("Error in handshake: " + exception.getMessage());
                        }
                    }
                });
    }


    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/app");
        registry.enableSimpleBroker("/topic", "/queue");
        registry.setUserDestinationPrefix("/user");
    }

    @Override
    public boolean configureMessageConverters(List<MessageConverter> messageConverters) {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        ObjectMapper objectMapper = converter.getObjectMapper();
        objectMapper.registerSubtypes(MessageRequestDto.class); // Đăng ký MessageRequestDto
        messageConverters.add(0, converter);
        return false;
    }

    @Bean
    public WebSocketContainerFactoryBean createWebSocketContainer() {
        WebSocketContainerFactoryBean container = new WebSocketContainerFactoryBean();
        container.setMaxSessionIdleTimeout(60000L); // 1 minute
        return container;
    }


}
