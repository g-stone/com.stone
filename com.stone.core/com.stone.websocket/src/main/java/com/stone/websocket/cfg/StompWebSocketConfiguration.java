package com.stone.websocket.cfg;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class StompWebSocketConfiguration implements WebSocketMessageBrokerConfigurer {
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		System.out.println("注册");
		registry
			.addEndpoint("/hello")
			.withSockJS();
	}
	
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		System.out.println("启动");
		registry.enableSimpleBroker("/topic");
		registry.setApplicationDestinationPrefixes("/app");
	}
}
