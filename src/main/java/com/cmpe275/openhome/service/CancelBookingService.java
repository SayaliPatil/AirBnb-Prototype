package com.cmpe275.openhome.service;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.cmpe275.openhome.model.Booking;
import com.cmpe275.openhome.utils.DateUtility;
import com.cmpe275.openhome.utils.EmailUtility;

@Component
public class CancelBookingService {

	@Autowired
	private BookingService bookingService;
	
	@Autowired
	private CheckInOutService checkInOutService;

	@Autowired
	private DateUtility dateUtility;
	
	@Autowired
	private TimeSet timeSet;
	
	public void updateBookingAfterCancellation(Booking booking) {
		long timeDifference = dateUtility.timeDifference(booking.getCheck_in_date() + " 15:00:00");
		Date currentDateTime = timeSet.getDate();
    	if(currentDateTime == null) {
    		currentDateTime = Calendar.getInstance().getTime();
    	}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(currentDateTime);
		System.out.println("DAY OF WEEK : " +calendar.get(Calendar.DAY_OF_WEEK));
		System.out.println("timeDifference : " +timeDifference);
		int firstDay = calendar.get(Calendar.DAY_OF_WEEK);
		int secondDay = firstDay == 7 ? (firstDay + 1) % 7 : firstDay + 1;
		double totalFine;
		if(!booking.isUser_checked_in_flag() && timeDifference > 1440) {
			booking.setAmount_paid(0);
		}
		else if(!booking.isUser_checked_in_flag() && timeDifference <= 1440 && timeDifference > 0) {
			if(firstDay == 1 || firstDay == 7) {
				totalFine = booking.getWeekendprice() * 0.3;
			}
			else {
				totalFine = booking.getWeekdayprice() * 0.3;
			}
			booking.setAmount_paid(totalFine);
		}
		else if((!booking.isUser_checked_in_flag() && timeDifference <= 0)) {
			totalFine = findPerDayFine(booking.getTotal_nights(), firstDay, secondDay, booking);
			booking.setAmount_paid(totalFine);
		}
		else if(booking.isUser_checked_in_flag()) {
			long dayDifference = dateUtility.dateDifference(dateUtility.getStringDate(currentDateTime) , booking.getCheck_out_date());
			System.out.println("dayDifference : "+dayDifference);
			totalFine = findPerDayFine(dayDifference, firstDay, secondDay, booking);
			System.out.println("totalFine : "+totalFine);
			long totalStayDays = dateUtility.dateDifference(booking.getCheck_in_date(), dateUtility.getStringDate(currentDateTime));
			System.out.println("totalStayDays : " +totalStayDays);
			double totalStayRent = 0;
			calendar.setTime(dateUtility.getDate(booking.getCheck_in_date()));
			int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
			totalStayRent = findTotalRentPaid(totalStayDays , dayOfWeek , booking);
			booking.setAmount_paid(totalStayRent + totalFine);
			System.out.println("Total amount paid : " +booking.getAmount_paid());
		}
		booking.setBooking_cancelled(true);
		bookingService.saveBookingDetails(booking);
		checkInOutService.updateAccountDetails(booking, booking.getID(), -1 * booking.getAmount_paid(), booking.getAmount_paid());
		checkInOutService.sendCancellationNotification(EmailUtility.createCancellationConfirmationMsg() , EmailUtility.createCancellationConfirmationMsgHost(),
				booking.getUser_email() , booking.getHost_email());
		
	}
	
	public double findPerDayFine(long dateDifference , int firstDay , int secondDay , Booking booking) {
		double perDayFine = 0.0;
		System.out.println("firstDay : " +firstDay);
		System.out.println("secondDay : " +secondDay);
		if(dateDifference < 2) {
			if(firstDay == 1 || firstDay == 7) {
				perDayFine = booking.getWeekendprice() * 0.3;
			}
			else {
				perDayFine = booking.getWeekdayprice() * 0.3;
			}
		}
		else if( (firstDay == 1 || firstDay == 7) && (secondDay == 1 || secondDay == 7) && (dateDifference >= 2)) {
			perDayFine = booking.getWeekendprice() * 0.3 * 2; 
		}
		else if((firstDay == 1 || firstDay == 7) && (secondDay == 2 || secondDay == 3 || secondDay == 4 || secondDay == 5 || secondDay == 6) && (dateDifference >= 2)) {
			perDayFine = (booking.getWeekdayprice() + booking.getWeekendprice() ) * 0.3;
		}
		else if((secondDay == 1 || secondDay == 7) && (firstDay == 2 || firstDay == 3 || firstDay == 4 || firstDay == 5 || firstDay == 6) && (dateDifference >= 2)) {
			perDayFine = (booking.getWeekdayprice() + booking.getWeekendprice() ) * 0.3;
		}
		else if((secondDay == 2 || secondDay == 3 || secondDay == 4 || secondDay == 5 || secondDay == 6) && (firstDay == 2 || firstDay == 3 || firstDay == 4 || firstDay == 5 || firstDay == 6) && (dateDifference >= 2)) {
			perDayFine = booking.getWeekdayprice() * 0.3 * 2;
		}
		return perDayFine;
	}
	
	public double findTotalRentPaid(long totalStay, int dayOfWeek, Booking booking ) {
		double totalStayRent = 0.0;
		for(int i = 0 ; i < totalStay ; i++) {
			if(dayOfWeek > 7) {
				dayOfWeek %= 7;
			}
			System.out.println("dayOfWeek : " +dayOfWeek);
			if(dayOfWeek == 1 || dayOfWeek == 7) {
				totalStayRent += booking.getWeekendprice();
			}
			else {
				totalStayRent += booking.getWeekdayprice();
			}
			dayOfWeek++;
		}
		return totalStayRent;
	}
}
