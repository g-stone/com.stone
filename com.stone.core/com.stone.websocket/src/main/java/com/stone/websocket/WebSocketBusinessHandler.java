package com.stone.websocket;

import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import javax.annotation.Resource;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import com.stone.tools.ConverteUtils;

@Component("businessHandler")
public class WebSocketBusinessHandler implements WebSocketHandler {
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		String uid = (String) session.getAttributes().get("uid");
		logger.info("Session {} connected.", uid);
		
		// 如果是新 Session，记录进 Map
		boolean isNewUser = true;
		for (Entry<String, Set<WebSocketSession>> entry : userSocketSessionMap.entrySet()) {
			String key = entry.getKey();
			if (key.equals(uid)) {
				userSocketSessionMap.get(uid).add(session);
				isNewUser = false;
				break;
			}
		}
		if (isNewUser) {
			Set<WebSocketSession> sessions = new HashSet<>();
			sessions.add(session);
			userSocketSessionMap.put(uid, sessions);
		}
		logger.info("当前在线用户数: {}", userSocketSessionMap.values().size());
	}
	
	@Override
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
		if (message.getPayloadLength() == 0) {
			return;
		}
		
		Map<String, Object> meta = ConverteUtils.toMap(message.getPayload().toString());
		
		meta.put("date", new Date());
		sendMessageToUser(meta.get("to").toString(), new TextMessage(message.getPayload().toString()));
	}
	
	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		if (session.isOpen()) {
			session.close();
		}
		// 移除Socket会话
		for (Set<WebSocketSession> item : userSocketSessionMap.values()) {
			if (item.contains(session)) {
				// 删除连接 session
				item.remove(session);
				// 如果 userId 对应的 session 数为 0 ，删除该 userId 对应的记录
				if (0 == item.size()) {
					userSocketSessionMap.values().remove(item);
				}
				break;
			}
		}
	}
	
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
		logger.info("Session {} disconnected. Because of {}", session.getId(), closeStatus);
		for (Set<WebSocketSession> item : userSocketSessionMap.values()) {
			if (item.contains(session)) {
				// 删除连接 session
				item.remove(session);
				// 如果 userId 对应的 session 数为 0 ，删除该 userId 对应的记录
				if (0 == item.size()) {
					userSocketSessionMap.values().remove(item);
				}
				break;
			}
		}
		logger.info("当前在线用户数: {}", userSocketSessionMap.values().size());
	}
	
	@Override
	public boolean supportsPartialMessages() {
		return false;
	}
	
	/**
	 * 给某个用户发送消息
	 * @param userName
	 * @param message
	 * @throws IOException
	 */
	private void sendMessageToUser(String uid, TextMessage message) throws IOException {
		for (String id : userSocketSessionMap.keySet()) {
			if (id.equals(uid)) {
				for (WebSocketSession session : userSocketSessionMap.get(uid)) {
					try {
						logger.info("SendAll: {}", message);
						session.sendMessage(message);
					} catch (Exception e) {
						logger.error(e.toString());
					}
				}
			}
		}
	}
	
	/**
	 * 给所有在线用户发送消息
	 *
	 * @param message
	 * @throws IOException
	 */
	public void broadcast(final TextMessage message) throws IOException {
		// 多线程群发
		// template.convertAndSend("/topic/getLog", message.toString()); // 这里用于广播
		for(Set<WebSocketSession> item : userSocketSessionMap.values()) {
			for (final WebSocketSession session : item) {
				if (session.isOpen()) {
					ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1,
						new BasicThreadFactory.Builder().namingPattern("socket-schedule-pool-%d").daemon(true).build());
					for (int i = 0; i < 3; i++) {
						executorService.execute(new Runnable() {
							@Override
							public void run() {
								try {
									if (session.isOpen()) {
										logger.debug("broadcast output:" + message.toString());
										session.sendMessage(message);
									}
								} catch (IOException e) {
									e.printStackTrace();
								}
							}
						});
					}
				}
			}
		}
	}
	
	private SimpMessagingTemplate template;
	private static final Logger logger = LoggerFactory.getLogger(WebSocketBusinessHandler.class);
	private static Map<String, Set<WebSocketSession>> userSocketSessionMap = new ConcurrentHashMap<>();
	public SimpMessagingTemplate getTemplate() {
		return template;
	}
	@Resource(name = "template")
	public void setTemplate(SimpMessagingTemplate template) {
		this.template = template;
	}
}
