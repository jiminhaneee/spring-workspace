package com.kh.hyper.common.mail;

import java.util.Properties;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

// 직접 객체를 생성해서 이메일을 보내는 샘플코드
// 필요한 라이브러리 목록
// spring-context-support
// java mail-api
public class MailTest1 {

	public static void main(String[] args) {

		JavaMailSenderImpl sender = new JavaMailSenderImpl(); //객체 생성 : 이 객체가지고 이메일 보냄
		
		// 계정 설정
		sender.setHost("smtp.gmail.com"); // 구글 메일우체국 사용(구글 메일서버사용)
		// 포드번호 지정
		sender.setPort(587); //: 포트번호지정( 구글 포트번호 587)
		// 각자의 정보를 입력해줘야함
		sender.setUsername("1124mjh");
		sender.setPassword("pcelsfmnrsdzmvrf");
		
		// 옵션 설정
		Properties options = new Properties();
		options.put("mail.smtp.auth", true);
		options.put("mail.smtp.starttls.enable", true);
		
		sender.setJavaMailProperties(options);
		
		// 메일로 보낼 메세지 생성
		SimpleMailMessage message = new SimpleMailMessage();
		
		// 메세지 정보 : 제목, 내용, 받는사람, 참조, 숨은참조, 첨부파일(SimpleMailMessage로는 불가능)
		
		message.setSubject("새해 복 많이 받으세요~");
		message.setText("행운의 편지");
		//message.setTo("1227wanho@gmail.com"); 
		//여러명에게 보낼경우 배열을 만들어서 보내면 됨
		String[] to = {"1227wanho@gmail.com", "1124mjh@gmail.com"};
		message.setTo(to);//받는이
		
		sender.send(message); // 보내기 버튼역할
		
		/*
		 * 참조
		 * :
		 * 메세지 객체.setCc(참조할주소)
		 * --------------------------------
		 * 숨은참조
		 * :
		 * 메세지 객체.setBcc(숨은 참조할 주소)
		 * 
		 */
		
		
	}

}
