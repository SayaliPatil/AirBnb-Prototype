package com.cmpe275.openhome.controller;

import java.net.URISyntaxException;

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
import com.cmpe275.openhome.model.Card;
import com.cmpe275.openhome.model.User;
import com.cmpe275.openhome.repository.BookingRepository;
import com.cmpe275.openhome.service.BookingService;
import com.cmpe275.openhome.service.UserService;

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
	
	@PostMapping("/book")
    @ResponseBody
    public ResponseEntity<String> registration(@Valid @RequestBody Booking booking) throws URISyntaxException {
    	System.out.println("Body sent : " +booking.getUser_email());
    	User existingUser = userService.findByEmail(booking.getUser_email());
    	if(existingUser == null) {
    		System.out.println("User does not exist exist");
    		return new ResponseEntity<>("{\"status\" : \"No user found with sent email id.!!\"}", HttpStatus.BAD_REQUEST);
    	}
    	bookingService.saveBookingDetails(booking);
        return new ResponseEntity<>("{\"status\" : \"User's card details saved successfully.!!\"}", HttpStatus.OK);
    }
}
