package com.kh.hyper.common.mail;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MailTest3 {
	
	@Autowired
	private JavaMailSenderImpl sender;
	
	@GetMapping("sendmail")
	public String sendMail() throws MessagingException {
		
		// MimeMessage객체 생성
		MimeMessage message = sender.createMimeMessage();
		
		MimeMessageHelper helper = new MimeMessageHelper(message, false, "UTF-8"); // 인자값 = 내용/파일첨부여부/인코딩
		
		//helper.setSubject("형태가 있는 메일이란 무엇인가?");
		
		/*
		helper.setText("<h1 style='color:red; font-size:60px;'>방법이 없어요</h1>"
					  + "<div style='font-size:48px;'>어쩌겠어요 이게 싫으면 ~<div>", true);
		*/
		
		helper.setSubject("[hyper]비밀번호 변경 안내 메일입니다.");
		String authCode="123";
		
		String url = "<a href='http://localhost/hyper/auth?authCode=123'>요기로와서 진행하세요~</a>";
		
		helper.setText(url,true);
		
		helper.setTo("1124mjh@gmail.com"); 
		
		sender.send(message);
		
		
		return "redirect:/";
	}
	
	@GetMapping("auth")
	public String auth(String authCode) {
		
		//DB에 저장해놓은 authCode 조회해오기
		
		if(authCode.equals("123")) {
			// 비밀번호 변경페이지로 이동~
			System.out.println("인증성공");
		}else {
			//잘못된 접근입니다.
			System.out.println("왜 없는 코드로 접근함?");
		}
		return "redirect:/";
	}

	
	
	
	
	
}
