package com.kh.socket;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/*
 * 웹소켓 서버로 만들기
 * -> 소켓핸들러 상속 / 인터페이스 구현 하는 방법 2가지가 있음 
 * 
 */
//public class WebSocketServer implements WebSocketHandler{
public class WebSocketServer extends TextWebSocketHandler{

	/**
	 * - 클라이언트가 웹 소캣 서버에 접속을 하면 호출되는 메소드 
	 * - session : 접속한 사용자의 웹 소켓 정보(HttpSession과는 아무런 연관이 없음)
	 */
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		System.out.println("전화받았습니다!");
		
		System.out.println(session);// 소캣에 접속한 사용자를 구분할 수 있는 값(사용자마다 다른 값이 들어온다)
	}
	
	
	/**
	 * - 클라이언트로부터 메세지 수신 시 호출되는 메소드 
	 * - session : 사용자(실제 메시지를 전송한 친구)의 소캣정보(HttpSession과는 아무런 연관도 존재하지 않음)
	 * - message : [ payload : 실제 전송 Data, byteCount : 보낸 메세지 크기(Byte단위), last : 메세지 종료 여부]
	 * 
	 */

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		System.out.println("메세지 수신 완료!");
		
		System.out.println(session);
		System.out.println(message);
		
		session.sendMessage(message);
		
	}
	
	/**
	 * - 클라이언트가 소캣서버로부터 접속을 종료 했을 때 호출되는 메소드
	 * - session : 접속되어있던 사용자의 세션정보(HttpSession과는 하나도 연관이 없다)
	 * - status : 접속이 종료된 원인과 관련된 정보
	 *  
	 * 
	 */
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		System.out.println("전화 끊었어요~");
		System.out.println(session);
		System.out.println(status);
	}
	
	 
	
	
	
	
	
	
}
