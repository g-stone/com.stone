package com.stone.websocket.cfg;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.stone.websocket.HandShake;
import com.stone.websocket.WebSocketBusinessHandler;

@Configuration
@EnableWebSocket
public class WebSocketConfiguration implements WebSocketConfigurer {
	
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(businessHandler(), "/ws").addInterceptors(new HandShake());
		registry.addHandler(businessHandler(), "/ws/sockjs").addInterceptors(new HandShake()).withSockJS();
	}
	
	@Bean
	public WebSocketBusinessHandler businessHandler() {
		return new WebSocketBusinessHandler();
	}
}
