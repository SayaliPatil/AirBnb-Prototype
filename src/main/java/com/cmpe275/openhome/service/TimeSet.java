package com.cmpe275.openhome.service;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

@Service
public class TimeSet {
	
	private Date date;
	
	TimeSet(){
		this.date = Calendar.getInstance().getTime();
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}

