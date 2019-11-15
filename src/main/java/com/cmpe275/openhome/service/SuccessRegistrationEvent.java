package com.cmpe275.openhome.service;

import java.util.Locale;

import org.springframework.context.ApplicationEvent;

import com.cmpe275.openhome.model.User;

import lombok.Data;

@Data
public class SuccessRegistrationEvent extends ApplicationEvent {
	private User user;
	private String url;
	private Locale locale;
	
	public SuccessRegistrationEvent(User user , Locale locale , String url) {
		super(user);
		this.user = user;
		this.locale = locale;
		this.url = url;
	}
}
