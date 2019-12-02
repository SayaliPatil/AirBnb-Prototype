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
}
