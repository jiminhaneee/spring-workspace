package com.kh.hyper.common.mail;

import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MailTest4 {
	
	private final JavaMailSenderImpl sender;
	
	@GetMapping("file-mail")
	public String sendFile() throws MessagingException {
		
		// MimeMessage 파일보낼때도 필요
		MimeMessage message = sender.createMimeMessage();
		
		MimeMessageHelper helper = new MimeMessageHelper(message,true,"UTF-8");
		
		String[] to = { "1124mjh@gmail.com", "ncs990808@gmail.com"};
		
		helper.setTo(to);
		helper.setSubject("파일파일");
		helper.setText("하뚜");
		
		//파일첨부
		//DataSource
		DataSource source = new FileDataSource("C:\\b-class0909\\dev\\mini.jpg"); //파일루트
		helper.addAttachment(source.getName(), source);
		
		sender.send(message);
		
		return "redirect:/";
	}
	

}
