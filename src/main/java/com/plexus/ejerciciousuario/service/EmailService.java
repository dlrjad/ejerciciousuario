package com.plexus.ejerciciousuario.service;

import static com.plexus.ejerciciousuario.constant.Constants.ADDRESSE;
import static com.plexus.ejerciciousuario.constant.Constants.SUBJECT;

import java.security.SecureRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class EmailService {

    @Autowired
    private TemplateEngine templateEngine;

		@Autowired private JavaMailSender mailSender;
		
		private String password;
		private int length = 10;
		private String characterSet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public String sendMail(String userName, String userEmail) {
			
			this.password = randomString(this.length, this.characterSet);

			try {
				MimeMessage mail = mailSender.createMimeMessage();

				MimeMessageHelper helper = new MimeMessageHelper( mail );
				//helper.setTo(userEmail);
				helper.setTo(ADDRESSE);
				helper.setSubject(SUBJECT);
				helper.setText(buildMessage(userName, this.password), true);
				mailSender.send(mail);

				return this.password;
			} catch (Exception e) {
				e.printStackTrace();
				return "Error al enviar email / Error to send email";
			}
    }

    public String buildMessage(String message, String password){
        Context context = new Context();
				context.setVariable("message", message);
				context.setVariable("messageBody", password);
        return templateEngine.process("emailTemplate", context);
		}

    public String randomString(int length, String characterSet) {
        return IntStream.range(0, length).map(i -> new SecureRandom().nextInt(characterSet.length())).mapToObj(randomInt -> characterSet.substring(randomInt, randomInt + 1)).collect(Collectors.joining());
    }

}