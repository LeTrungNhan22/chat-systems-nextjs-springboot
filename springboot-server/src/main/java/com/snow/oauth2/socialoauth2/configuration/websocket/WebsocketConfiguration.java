package com.snow.oauth2.socialoauth2.configuration.websocket;

import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

@Configuration
@EnableWebSocketMessageBroker
@Order(Ordered.HIGHEST_PRECEDENCE + 1) // Ưu tiên cấu hình WebSocket
public class WebsocketConfiguration implements WebSocketMessageBrokerConfigurer {


    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry
                .addEndpoint("/ws") // Your WebSocket endpoint
                .setAllowedOriginPatterns("*")  // Allow all origins (adjust for production)
                .withSockJS()
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
                            }
                        }

                        // Add the "server" header here
                        response.getHeaders().add("server", "WebSocket Server");
                        return true;
                    }

                    @Override
                    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
                        // Do nothing
                        if (exception != null) {
                            LoggerFactory.getLogger(WebsocketConfiguration.class).error("Error in handshake: " + exception.getMessage());
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


}
