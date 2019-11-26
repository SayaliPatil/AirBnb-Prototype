package com.cmpe275.openhome.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmpe275.openhome.model.Booking;
import com.cmpe275.openhome.repository.BookingRepository;

@Service
public class BookingService {
	@Autowired
    private BookingRepository bookingRepository;
	
	public void saveBookingDetails(Booking booking) {
		// TODO Auto-generated method stub
		System.out.println("card details : " +booking.getCheck_out_date());
		bookingRepository.save(booking);
	}

}
