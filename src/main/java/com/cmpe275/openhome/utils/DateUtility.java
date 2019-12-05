package com.cmpe275.openhome.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import com.cmpe275.openhome.exception.CustomException;

public class DateUtility {
	
	private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	private static final String DATE_PARSING_EXCEPTION_MESSAGE = "Date passed in string format can not be parsed ";
	
	
	public static String getStringDate(Date passedDate) { 
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  
        String strDate = dateFormat.format(passedDate);  
        System.out.println("Converted String: " + strDate);  
		return strDate;
	}
	public static String todayDate(int offset) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setTimeZone(TimeZone.getTimeZone("US/Pacific"));
		Calendar date = Calendar.getInstance();
		date.add(Calendar.DAY_OF_MONTH, offset);
		return dateFormat.format(date.getTime());
	}
	
	public static Date getDate(String date) {
		try {
			return dateFormat.parse(date);
		} catch (ParseException e) {
			throw new CustomException(DATE_PARSING_EXCEPTION_MESSAGE + e.getMessage());
		}
	}
	
	public static long dateDifference(String userCheckOut , String checkOut)  {
		Date startDate = null;
		Date endDate = null;
		long differenceInDays;
		try {
			startDate = dateFormat.parse(userCheckOut);
			endDate = dateFormat.parse(checkOut);
			long difference = Math.abs(endDate.getTime() - startDate.getTime());
			differenceInDays = TimeUnit.DAYS.convert(difference, TimeUnit.MILLISECONDS);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			throw new CustomException(DATE_PARSING_EXCEPTION_MESSAGE + e.getMessage()); 
		}
		return differenceInDays;
	}
	
	public static long timeDifference(String userCheckinDate)  {
		Date startDate = null;
		Date endDate = null;
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		dateFormat.setTimeZone(TimeZone.getTimeZone("US/Pacific"));
		Calendar date = Calendar.getInstance();
		date.add(Calendar.DAY_OF_MONTH, 0);
		System.out.println("userCheckinDate : " +userCheckinDate);
		System.out.println("TODAY : "+todayDate(0));
		
		try {
			startDate = dateFormat.parse(dateFormat.format(date.getTime()));
			endDate = dateFormat.parse(userCheckinDate);
			System.out.println("startDate : " +startDate);
			System.out.println("endDate : " +endDate);
			long difference = Math.abs(endDate.getTime() - startDate.getTime());
			return TimeUnit.MILLISECONDS.toMinutes(difference);  
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			throw new CustomException(DATE_PARSING_EXCEPTION_MESSAGE + e.getMessage()); 
		}
	}
	
	public static String findMonth(int m) {
		if(m == 0) {
			return "January";
		}
		else if(m == 1) {
			return "February";
		}
		else if(m == 2) {
			return "March";
		}
		else if(m == 3) {
			return "April";
		}
		else if(m == 4) {
			return "May";
		}
		else if(m == 5) {
			return "June";
		}
		else if(m == 6) {
			return "July";
		}
		else if(m == 7) {
			return "August";
		}
		else if(m == 8) {
			return "September";
		}
		else if(m == 9) {
			return "October";
		}
		else if(m == 10) {
			return "November";
		}
		else  {
			return "December";
		}
	}
	
}
