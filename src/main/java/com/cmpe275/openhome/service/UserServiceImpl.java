package com.cmpe275.openhome.service;

import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.cmpe275.openhome.model.User;
import com.cmpe275.openhome.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
    private ServletContext servletContext;
	
	@Autowired
	ApplicationEventPublisher eventPublisher;
	
	@Autowired
    private UserRepository userRepository;
	
	@Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	public void save(User user) {
		// TODO Auto-generated method stub
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		System.out.println("Password: " +user.getPassword());
		userRepository.save(user);
		
	}

	@Override
	public User findByEmail(String email) {
		// TODO Auto-generated method stub
		return userRepository.findByEmail(email);
	}
	
	public void sendVerificationEmail(User user, HttpServletRequest request) {
		try {
			String url = request.getContextPath();
			Locale locale = request.getLocale();
			eventPublisher.publishEvent(new SuccessRegistrationEvent(user , locale, url));
		}
		catch(Exception exception) {
			
		}
	}
}
