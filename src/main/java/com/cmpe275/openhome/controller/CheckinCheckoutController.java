package com.cmpe275.openhome.controller;

import java.net.URISyntaxException;
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
    	if(existingBooking.get() == null ) {
    		return new ResponseEntity<>("{\"status\" : \"No confirmed booking found with given booking id.!!\"}", HttpStatus.BAD_REQUEST);
    	}	
    	Booking book = bookingService.saveBookingDetails(booking);
    	if(booking.isUser_checked_in_flag() && !booking.isUser_checked_out_flag() && !booking.isBooking_cancelled()) {
    		checkinService.sendCheckinoutNotification(EmailUtility.createCheckInConfirmationMsg(), 
    					EmailUtility.createCheckInConfirmationMsgHost(), booking.getUser_email(), booking.getHost_email());
            
    	}
    	else if(booking.isUser_checked_in_flag() && booking.isUser_checked_out_flag() && !booking.isBooking_cancelled()) {
    		checkinService.sendCheckinoutNotification(EmailUtility.createCheckOutConfirmationMsg(), 
    				EmailUtility.createCheckOutConfirmationMsgHost(), booking.getUser_email(), booking.getHost_email());

    	}
    	return ResponseEntity.ok(book);
    }
}
    