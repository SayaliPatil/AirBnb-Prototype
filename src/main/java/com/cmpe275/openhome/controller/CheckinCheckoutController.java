package com.cmpe275.openhome.controller;

import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cmpe275.openhome.exception.CustomException;
import com.cmpe275.openhome.model.Booking;
import com.cmpe275.openhome.model.Card;
import com.cmpe275.openhome.model.User;
import com.cmpe275.openhome.repository.BookingRepository;
import com.cmpe275.openhome.service.BookingService;
import com.cmpe275.openhome.service.CheckInOutService;
import com.cmpe275.openhome.service.EmailService;
import com.cmpe275.openhome.service.UserService;
import com.cmpe275.openhome.utils.DateUtility;
import com.cmpe275.openhome.utils.EmailUtility;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class CheckinCheckoutController {
	@Autowired
	private BookingRepository bookingRepository;
	
	@Autowired
	private BookingService bookingService;
	
	@Autowired
	private CheckInOutService checkinService;
	
	@PostMapping("/checkinout")
    @ResponseBody
    public ResponseEntity<?> booking(@Valid @RequestBody Booking booking) throws URISyntaxException {
    	System.out.println("Body sent : " +booking);
    	Optional<Booking> existingBooking = bookingRepository.findById(booking.getID());
    	System.out.println("existingBooking : " +existingBooking.get());
    	if(existingBooking.get() == null ) {
    		return new ResponseEntity<>("{\"status\" : \"No confirmed booking found with given booking id.!!\"}", HttpStatus.BAD_REQUEST);
    	}
    	if(booking.isBooking_cancelled()) {
    		long differenceInDays = DateUtility.dateDifference(DateUtility.todayDate(0), existingBooking.get().getCheck_in_date());
    		
    		checkinService.updatePropertyAvailibilty(booking.getProperty_unique_id() , DateUtility.todayDate(2));
    	}
    	else if(existingBooking.get().isUser_checked_in_flag() && existingBooking.get().getCheck_out_date().compareTo(booking.getUser_check_out_date()) > 0) {
    		System.out.println("USER CHECK OUT DATE : " +booking.getUser_check_out_date().trim());
    		System.out.println("ACTUAL CHECK OUT DATE : " +booking.getCheck_out_date());
    		long differenceInDays = DateUtility.dateDifference(booking.getUser_check_out_date().trim(), existingBooking.get().getCheck_out_date());
    		System.out.println("differenceInDays : " +differenceInDays);
    		double perNightRent = existingBooking.get().getPrice() / existingBooking.get().getTotal_nights() ;
    		double perDayFine = perNightRent * 0.3;
    		System.out.println("perDayFine : " +perDayFine);
    		double rentPaid = (perNightRent) * differenceInDays; 
    		System.out.println("rentPaid : " +rentPaid);
			if(differenceInDays >= 2) {
				double totalFine = perDayFine * 2;
				booking.setAmount_paid(existingBooking.get().getPrice() + totalFine - rentPaid);
			}
			else {
				booking.setAmount_paid(existingBooking.get().getPrice() + perDayFine - rentPaid);
			}
			booking.setUser_check_out_date(DateUtility.todayDate(0));
			checkinService.updatePropertyAvailibilty(booking.getProperty_unique_id() , booking.getUser_check_out_date());
    	}
    	bookingService.saveBookingDetails(booking);
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
}
    