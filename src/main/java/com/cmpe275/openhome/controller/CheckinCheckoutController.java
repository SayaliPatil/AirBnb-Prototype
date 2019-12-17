package com.cmpe275.openhome.controller;

import java.net.URISyntaxException;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.cmpe275.openhome.model.Booking;
import com.cmpe275.openhome.repository.BookingRepository;
import com.cmpe275.openhome.service.BookingService;
import com.cmpe275.openhome.service.CancelBookingService;
import com.cmpe275.openhome.service.CheckInOutService;
import com.cmpe275.openhome.service.TimeSet;
import com.cmpe275.openhome.utils.DateUtility;
import com.cmpe275.openhome.utils.EmailUtility;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class CheckinCheckoutController {
	@Autowired
	private BookingRepository bookingRepository;
	
	@Autowired
	private BookingService bookingService;
	
	@Autowired
	private CheckInOutService checkinService;
	
	@Autowired
	private CancelBookingService cancelBookingService;
	
	@Autowired
	private TimeSet timeSet;
	
	@Autowired
	private DateUtility dateUtility;
	
	@PostMapping("/checkinout")
    @ResponseBody
    public ResponseEntity<?> checkinout(@Valid @RequestBody Booking booking) throws URISyntaxException {
    	System.out.println("Body sent : " +booking);
    	Optional<Booking> existingBooking = bookingRepository.findById(booking.getID());
    	System.out.println("existingBooking : " +existingBooking.get());
    	Date currentDateTime = timeSet.getDate();
    	if(currentDateTime == null) {
    		currentDateTime = Calendar.getInstance().getTime();
    	}
    	System.out.println("currentDateTime : " +currentDateTime);
    	if(existingBooking.get() == null ) {
    		return new ResponseEntity<>("{\"status\" : \"No confirmed booking found with given booking id.!!\"}", HttpStatus.BAD_REQUEST);
    	}
    	System.out.println("User check in flag : " +booking.isUser_checked_in_flag());
    	System.out.println("Check in date of booking : " +existingBooking.get().getCheck_in_date());
    	System.out.println("Check in date of provided by user : " +dateUtility.getStringDate(currentDateTime));
    	int checkinDifference = existingBooking.get().getCheck_in_date().compareTo(dateUtility.getStringDate(currentDateTime));
    	System.out.println("Compare dates : " +checkinDifference);
    	if(booking.isUser_checked_in_flag() && checkinDifference != 0 ) {
    		return new ResponseEntity<>("{\"status\" : \"user is not allowed to check in.!!\"}", HttpStatus.NOT_ACCEPTABLE);
    	}
    	else if(booking.isUser_checked_in_flag() && !existingBooking.get().isUser_checked_out_flag()) {
    		long totalStay = dateUtility.dateDifference(dateUtility.getStringDate(currentDateTime) , booking.getCheck_out_date());
    		System.out.println("totalStay : " +totalStay);
    		Calendar calendar = Calendar.getInstance();
    		calendar.setTime(currentDateTime);
    		System.out.println("DAY OF WEEK : " +calendar.get(Calendar.DAY_OF_WEEK));
    		int firstDay = calendar.get(Calendar.DAY_OF_WEEK);
    		double totalRentPaid = cancelBookingService.findTotalRentPaid(totalStay , firstDay, booking);
    		booking.setAmount_paid(totalRentPaid);
    	}
    	else if(existingBooking.get().isUser_checked_in_flag() && booking.getUser_check_out_date().compareTo(dateUtility.getStringDate(currentDateTime)) > 0) {
    		long differenceInDays = dateUtility.dateDifference(booking.getUser_check_out_date().trim(), dateUtility.getStringDate(currentDateTime));
    		Calendar calendar = Calendar.getInstance();
    		calendar.setTime(currentDateTime);
    		System.out.println("DAY OF WEEK : " +calendar.get(Calendar.DAY_OF_WEEK));
    		int firstDay = calendar.get(Calendar.DAY_OF_WEEK);
    		int secondDay = firstDay == 7 ? (firstDay + 1) % 7 : firstDay + 1;
    		double totalFine = cancelBookingService.findPerDayFine(differenceInDays, firstDay, secondDay, booking);
    		long totalStay = dateUtility.dateDifference(booking.getCheck_in_date(), dateUtility.getStringDate(currentDateTime));
    		calendar.setTime(dateUtility.getDate(booking.getCheck_in_date()));
    		int checkinDay = calendar.get(Calendar.DAY_OF_WEEK);
    		double totalRentPaid = cancelBookingService.findTotalRentPaid(totalStay , checkinDay, booking);
    		booking.setAmount_paid(totalRentPaid + totalFine);
			booking.setUser_check_out_date(dateUtility.getStringDate(currentDateTime));
    	}
    	bookingService.saveBookingDetails(booking);
    	checkinService.updateAccountDetails(booking, booking.getID(), -1 * booking.getAmount_paid(), booking.getAmount_paid());
    	if(booking.isUser_checked_in_flag() && !booking.isUser_checked_out_flag() && !booking.isBooking_cancelled()) {
    		checkinService.sendCheckinoutNotification(EmailUtility.createCheckInConfirmationMsg(), 
    					EmailUtility.createCheckInConfirmationMsgHost(), booking.getUser_email(), booking.getHost_email());
            
    	}
    	else if(booking.isUser_checked_in_flag() && booking.isUser_checked_out_flag() && !booking.isBooking_cancelled()) {
    		checkinService.sendCheckinoutNotification(EmailUtility.createCheckOutConfirmationMsg(), 
    				EmailUtility.createCheckOutConfirmationMsgHost(), booking.getUser_email(), booking.getHost_email());

    	}
    	
    	return new ResponseEntity<>("{\"status\" : \"User has been successfully checked in/out!!\"}", HttpStatus.OK);
    }
	
	@PostMapping("/cancelbooking")
    @ResponseBody
    public ResponseEntity<?> cancel(@Valid @RequestBody Booking booking) throws URISyntaxException {
    	System.out.println("Body sent : " +booking);
    	Optional<Booking> existingBooking = bookingRepository.findById(booking.getID());
    	System.out.println("existingBooking : " +existingBooking.get());
    	if(existingBooking.get() == null ) {
    		return new ResponseEntity<>("{\"status\" : \"No confirmed booking found with given booking id.!!\"}", HttpStatus.BAD_REQUEST);
    	}
    	cancelBookingService.updateBookingAfterCancellation(existingBooking.get());
    	return new ResponseEntity<>("{\"status\" : \"Booking has been cancelled successfully!!\"}", HttpStatus.OK);
    }
	
}
    