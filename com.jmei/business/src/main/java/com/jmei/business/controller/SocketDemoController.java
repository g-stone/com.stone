package com.jmei.business.controller;

import javax.annotation.Resource;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.stone.websocket.WebSocketBusinessHandler;

@Controller
public class SocketDemoController {
	@ResponseBody
	@MessageMapping("/hello")
	@SendTo("/topic/greetings")
	public String greeting(@RequestParam("caseId") String message) throws Exception {
		System.out.println(message);
		Thread.sleep(3000);
		return "Hello, " + message + "!";
	}
	
	private SimpMessagingTemplate template;
	private WebSocketBusinessHandler businessHandler;
	public SimpMessagingTemplate getTemplate() {
		return template;
	}
	@Resource(name = "template")
	public void setTemplate(SimpMessagingTemplate template) {
		this.template = template;
	}
	public WebSocketBusinessHandler getBusinessHandler() {
		return businessHandler;
	}
	@Resource(name = "businessHandler")
	public void setBusinessHandler(WebSocketBusinessHandler businessHandler) {
		this.businessHandler = businessHandler;
	}
	
}
