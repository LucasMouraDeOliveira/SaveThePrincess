package com.lordkadoc;

import java.security.Principal;
import java.util.Map;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

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
        registry.addEndpoint(PATH_TO_WEBSOCKET_ENDPOINT)
        .addInterceptors(new HttpSessionHandshakeInterceptor())
        .setHandshakeHandler(new MyHandler())
        .withSockJS();
    }
    
    class MyHandler extends DefaultHandshakeHandler {

        @Override
        protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {

        	//Get the Username object which you have saved as session objects
        	String name  = (String)attributes.get("name");

        	//Return the User
        	return new UsernamePasswordAuthenticationToken(name, null);
        }
        
    }

}