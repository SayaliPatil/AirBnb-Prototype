package com.cmpe275.openhome.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cmpe275.openhome.exception.CustomException;
import com.cmpe275.openhome.model.Booking;
import com.cmpe275.openhome.model.Property;
import com.cmpe275.openhome.repository.BookingRepository;
import com.cmpe275.openhome.repository.PropertyRepository;
import com.cmpe275.openhome.utils.DateUtility;

@Service
public class BookingService {
	
	@Autowired
    private BookingRepository bookingRepository;
	
	@Autowired
	private PropertyRepository propertyRepository;
	
	@Autowired
	private EntityManager entityManager;
	
	private static final String FETCH_BOOKING_DETAILS_EXCEPTION_MESSAGE = "No booking details found for the user";
	public Booking saveBookingDetails(Booking booking) {
		// TODO Auto-generated method stub
		Booking book = null;
		try {
			book = bookingRepository.save(booking);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return book;
	}
	
	public List<Booking> getBookingDetails(String email) {
		System.out.println("booking details fetched: " +email);
		Query query = entityManager.createQuery("from Booking as b WHERE (b.user_email =:email)");
		query.setParameter("email", email);
		List<Booking> book = null;
		try {
			book = (List<Booking>) query.getResultList();
		}
		catch(Exception exception) {
			throw new CustomException(FETCH_BOOKING_DETAILS_EXCEPTION_MESSAGE + exception.getMessage());
		}
		return book;
	}
	
	public List<Booking> getBookingByProperty(Long property_id){
		return bookingRepository.findByPropertyId(property_id);
	}
	
	public Optional<Booking> getBookingById(Long booking_id) {
		return bookingRepository.findById(booking_id);
	}
	
	public boolean getBookingDetailsById(Property prop , String startDate , String endDate) {
		System.out.println("booking details fetched: " +prop.getId());
		System.out.println("Start Date : " +startDate + " End date : " +endDate);
		Query query = entityManager.createQuery("from Booking as b WHERE (b.propertyId =:id AND b.booking_cancelled =false "
				+ "AND ((b.check_in_date <= :startDate AND b.check_out_date >= :endDate) "
				+ "OR (b.check_in_date >= :startDate AND b.check_in_date <= :endDate) "
				+ "OR (b.check_out_date >= :startDate AND b.check_out_date <= :endDate) "
				+ " OR (b.check_in_date <= :startDate AND b.check_in_date <= :endDate "
				+ "AND b.check_out_date >= :startDate AND b.check_out_date <= :endDate)))");
		query.setParameter("id", prop.getId());
		query.setParameter("startDate", startDate);
		query.setParameter("endDate", endDate);
		System.out.println("Query : " +query.getHints());
		List<Booking> book = null;
		try {
			book = (List<Booking>) query.getResultList();
			System.out.println("BOOK : " +book.size());
		}
		catch(Exception exception) {
			throw new CustomException(FETCH_BOOKING_DETAILS_EXCEPTION_MESSAGE + exception.getMessage());
		}
		return book.size() == 0 ? false : true;
	}
}
