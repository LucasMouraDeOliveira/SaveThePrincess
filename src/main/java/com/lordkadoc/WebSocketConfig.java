package com.lordkadoc;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {
	
	public static final String PATH_TO_WEBSOCKET_ENDPOINT = "/stp-websocket-endpoint";
	
	public static final String SERVER_MESSAGE_PREFIX = "/stp-websocket-server";
	
	public static final String CLIENT_MESSAGE_PREFIX = "/stp-websocket-client";
	
	
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker(CLIENT_MESSAGE_PREFIX);
        config.setApplicationDestinationPrefixes(SERVER_MESSAGE_PREFIX);
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint(PATH_TO_WEBSOCKET_ENDPOINT).withSockJS();
    }

}