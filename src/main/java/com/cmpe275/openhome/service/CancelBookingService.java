package com.cmpe275.openhome.service;

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

	public void updateBookingAfterCancellation(Booking booking) {
		
		long timeDifference = DateUtility.timeDifference(booking.getCheck_in_date() + " 15:00:00");
		if(!booking.isUser_checked_in_flag() && timeDifference > 1440) {
			booking.setAmount_paid(0);
			checkInOutService.updatePropertyAvailibilty(booking.getProperty_unique_id() , DateUtility.todayDate(0));
		}
		else if((!booking.isUser_checked_in_flag() && timeDifference <= 1440)) {
			double perDayFine = (booking.getPrice() / booking.getTotal_nights()) * 0.3;
			if(booking.getTotal_nights() >= 2) {
				double totalFine = perDayFine * 2;
				booking.setAmount_paid(totalFine);
			}
			else {
				booking.setAmount_paid(perDayFine);
			}
			checkInOutService.updatePropertyAvailibilty(booking.getProperty_unique_id() , DateUtility.todayDate(2));
		}
		else if(booking.isUser_checked_in_flag()) {
			long dayDifference = DateUtility.dateDifference(DateUtility.todayDate(0) , booking.getCheck_out_date());
			double perNightRent = booking.getPrice() / booking.getTotal_nights() ;
    		double perDayFine = perNightRent * 0.3;
    		double rentPaid = (perNightRent) * dayDifference; 
			if(dayDifference >= 2) {
				double totalFine = perDayFine * 2;
				booking.setAmount_paid(booking.getPrice() + totalFine - rentPaid);
			}
			else {
				booking.setAmount_paid(booking.getPrice() + perDayFine - rentPaid);
			}
			checkInOutService.updatePropertyAvailibilty(booking.getProperty_unique_id() , DateUtility.todayDate(2));
		}
		booking.setBooking_cancelled(true);
		bookingService.saveBookingDetails(booking);
		checkInOutService.sendCancellationNotification(EmailUtility.createCancellationConfirmationMsg() , EmailUtility.createCancellationConfirmationMsgHost(),
				booking.getUser_email() , booking.getHost_email());
		
	}
}