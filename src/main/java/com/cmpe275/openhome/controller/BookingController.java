package com.cmpe275.openhome.controller;

import java.net.URISyntaxException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.cmpe275.openhome.model.User;
import com.cmpe275.openhome.repository.BookingRepository;
import com.cmpe275.openhome.service.BookingService;
import com.cmpe275.openhome.service.EmailService;
import com.cmpe275.openhome.service.UserService;
import com.cmpe275.openhome.utils.EmailUtility;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class BookingController {
	@Autowired
	BookingRepository bookingRepository;
	
	@Autowired
	BookingService bookingService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	EmailService emailService;
	
	private static final String BOOKING_CONFIRMATION_EXCEPTION_MESSAGE = "Booking and property tables could not updated";
	
	@PostMapping("/book")
    @ResponseBody
    public ResponseEntity<?> booking(@Valid @RequestBody Booking booking) throws URISyntaxException {
    	System.out.println("Body sent : " +booking);
    	User existingUser = userService.findByEmail(booking.getUser_email());
    	if(existingUser == null) {
    		System.out.println("User does not exist exist");
    		return new ResponseEntity<>("{\"status\" : \"No user found with sent email id.!!\"}", HttpStatus.BAD_REQUEST);
    	}	
    	Booking book = bookingService.saveBookingDetails(booking);
        try {
        	bookingService.updateBookedProperty(book);
        }
        catch(Exception e) {
        	throw new CustomException(BOOKING_CONFIRMATION_EXCEPTION_MESSAGE +e.getMessage()); 
        }
    	return ResponseEntity.ok(book);
    }
	
    @ResponseBody
    @RequestMapping(method=RequestMethod.GET, value = "/fetchBooking/{email}")
    public ResponseEntity<?> getBookingDetails(@PathVariable String email) {
    	System.out.println("User ID send as a parm : " +email);
    	List<Booking> book = bookingService.getBookingDetails(email);
    	return ResponseEntity.ok(book);
    }
    
    @PostMapping("/book/email")
    @ResponseBody
    public ResponseEntity<?> registration(@Valid @RequestBody Booking booking) throws URISyntaxException {
    	System.out.println("Body sent : " +booking);
    	String guestMessage = EmailUtility.createBookingConfirmationMsg();
    	String hostMessage = EmailUtility.createBookingConfirmationMsgHost();
        emailService.sendEmail(booking.getUser_email(), guestMessage, " Booking Confirmation with OpenHome.!!");
        emailService.sendEmail(booking.getHost_email(), hostMessage, " Booking Confirmation with OpenHome.!!");
        return new ResponseEntity<>("{\"status\" : \"Email has been sent successfully\"}", HttpStatus.OK);
    }
    
}
