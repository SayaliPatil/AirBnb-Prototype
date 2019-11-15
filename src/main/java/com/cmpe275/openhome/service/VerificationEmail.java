package com.cmpe275.openhome.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import com.cmpe275.openhome.model.User;

public class VerificationEmail implements ApplicationListener<SuccessRegistrationEvent>{
	
	@Autowired
    private JavaMailSender emailSender;
	
	@Autowired
    private IUserService iUserService;
  
    @Autowired
    private MessageSource source;
  
    
    
	@Override
	public void onApplicationEvent(SuccessRegistrationEvent event) {
		// TODO Auto-generated method stub
		this.successVerification(event);
	}
    private void successVerification(SuccessRegistrationEvent event) {
        User user = event.getUser();
        String uuid = UUID.randomUUID().toString();
        iUserService.createVerificationToken(user, uuid);
         
        String recipientAddress = user.getEmail();
        String subject = "Openhome registration verification email ";
        String confirmationUrl 
          = event.getUrl() + "/regitrationConfirm.html?token=" + uuid;
        String message = source.getMessage("message.regSucc", null, event.getLocale());
         
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(message + " rn" + "http://localhost:8080" + confirmationUrl);
        mailSender.send(email);
    }
}
