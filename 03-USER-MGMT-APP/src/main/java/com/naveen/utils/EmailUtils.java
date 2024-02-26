package com.naveen.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import jakarta.mail.internet.MimeMessage;

@Component
public class EmailUtils {

	@Autowired
	private JavaMailSender mailSender; // JavaMailSender is a predefined interface
	
	private Logger logger = LoggerFactory.getLogger(EmailUtils.class);

	public boolean sendEmail(String to, String subject, String body) {

		boolean isMailSent = false;

		try {

			MimeMessage mimeMessage = mailSender.createMimeMessage();

			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);

			helper.setTo(to);
//			helper.setCc(cc);
//			helper.setBcc(bcc);
			helper.setSubject(subject);
			helper.setText(body, true); 
			// if any hyperlink/URL is there in our body then we need mention 2nd parameter as true, otherwise no need give second parameter
//			helper.addAttachment(attachementFileName, dataSource);
			mailSender.send(mimeMessage);

			isMailSent = true;

		} catch (Exception e) {
//			e.printStackTrace();
			logger.error("Exception Occured", e);
		}

		return isMailSent;
	}
}
