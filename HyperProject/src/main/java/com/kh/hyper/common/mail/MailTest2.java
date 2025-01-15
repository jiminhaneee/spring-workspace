package com.kh.hyper.common.mail;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MailTest2 {
	
	private final JavaMailSenderImpl sender;
	
	@PostMapping("mail.test")
	public String sendMail(String subject, String text) {
		
		SimpleMailMessage msg = new SimpleMailMessage();
		
		// 제목, 내용, 받는사람
		msg.setSubject(subject);
		msg.setText(text);
		
		String[] to = {"1124mjh@gmail.com", "rkdtmxh@gmail.com"};
		
		msg.setTo(to);
		
		//메세지 전송
		sender.send(msg);
		
		return "redirect:/";
	}
	
	@GetMapping("mailpage")
	public String forwardMail() {
		return "mail/mail-test";
	}
	

}
