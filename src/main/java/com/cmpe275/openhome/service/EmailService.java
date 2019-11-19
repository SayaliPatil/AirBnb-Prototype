package com.cmpe275.openhome.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
	@Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String recipientAddress, String message, String subject) throws MailException {
        SimpleMailMessage sms = new SimpleMailMessage();
        sms.setTo(recipientAddress);
        sms.setSubject(subject);
        sms.setSentDate(new Date());
        sms.setText(message);
        System.out.println("Verification email has been sent to user");
        mailSender.send(sms);
    }
}
