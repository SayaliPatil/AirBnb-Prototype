package com.cmpe275.openhome.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmpe275.openhome.exception.CustomException;
import com.cmpe275.openhome.model.Booking;
import com.cmpe275.openhome.model.Card;
import com.cmpe275.openhome.repository.BookingRepository;

@Service
public class BookingService {
	
	@Autowired
    private BookingRepository bookingRepository;
	
	@Autowired
	private EntityManager entityManager;
	
	private static final String FETCH_BOOKING_DETAILS_EXCEPTION_MESSAGE = "No booking details found for the user";
	
	public Booking saveBookingDetails(Booking booking) {
		// TODO Auto-generated method stub
		System.out.println("card details : " +booking.getCheck_out_date());
		Booking book = bookingRepository.save(booking);
		return book;
	}
	
	public List<Booking> getBookingDetails(Long id) {
		System.out.println("booking details fetched: " +id);
		Query query = entityManager.createQuery("from Booking as b WHERE (b.property_id =:id)");
		query.setParameter("id", id);
		System.out.println("QUERY GENEARTED: " +query.getFirstResult());
		List<Booking> book = null;
		try {
			book = (List<Booking>) query.getResultList();
		}
		catch(Exception exception) {
			throw new CustomException(FETCH_BOOKING_DETAILS_EXCEPTION_MESSAGE);
		}
		return book;
	}
}
