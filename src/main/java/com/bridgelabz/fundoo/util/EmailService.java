package com.bridgelabz.fundoo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.bridgelabz.fundoo.exception.FundooException;

@Component
public class EmailService {

	@Autowired
	private JavaMailSender javaMailSender;
	
	public void sendMail(String subject,String text,String to, String from) {
		try {
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setSubject(subject);
		mail.setText(text);
		mail.setTo(to);
        mail.setFrom(from);
		javaMailSender.send(mail);
		}catch (Exception e) {
			e.printStackTrace();
			throw new FundooException(HttpStatus.BAD_GATEWAY.value(),"Error While sending email verification link");
		}

	}
}
