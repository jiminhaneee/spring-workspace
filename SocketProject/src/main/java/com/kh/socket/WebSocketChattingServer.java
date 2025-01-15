package com.kh.socket;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class WebSocketChattingServer extends TextWebSocketHandler{

	/*
	 * 	사용자들을 기억하기 위한 저장소
	 * 
	 * - 중복 불가
	 * - 동기화 지원
	 * 
	 */
	private Set<WebSocketSession> users = new CopyOnWriteArraySet();
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		users.add(session);
		System.out.println("사용자 접속! 현재 접속자 " + users.size() + "명 입니다!");
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		
		 TextMessage newMessage = new TextMessage((CharSequence)message.getPayload());
		 
		 //접속한 사용자를 알 수 없으므로, 저장소에 있는 만큼 반복해서 메세지 전송
		 for(WebSocketSession ws : users) {
			 ws.sendMessage(newMessage);
		 }
		
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		users.remove(session);
		System.out.println("사용자 종료! 현재 접속자 " + users.size() + "명 입니다!");
	}
	
	
	

}
