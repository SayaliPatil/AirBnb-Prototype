package com.cmpe275.openhome.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class DateUtility {
	public static String todayDate(int offset) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dateFormat.setTimeZone(TimeZone.getTimeZone("US/Pacific"));
		Calendar date = Calendar.getInstance();
		date.add(Calendar.DAY_OF_MONTH, offset);
		return dateFormat.format(date.getTime()).split(" ")[0];
	}
}
