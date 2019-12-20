package com.cmpe275.openhome.service;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
public class DashboardService {
	
	@Autowired
    private BookingRepository bookingRepository;
	
	@Autowired
	private PropertyRepository propertyRepository;
	
	@Autowired
	private EntityManager entityManager;
	
	private static final String FETCH_USER_BOOKING_DETAILS_EXCEPTION_MESSAGE = "No booking details found for the user to display on dashboard";

	public List<Booking> getDashboardDetails(String email) {
		System.out.println("user dashboard details fetched: " +email);
		Query query = entityManager.createQuery("from Booking as b WHERE (b.user_email =:email AND b.check_in_date >=:input )");
		
		query.setParameter("email",email);
		query.setParameter("input",DateUtility.todayDate(-365));
		List<Booking> book = null;
		try {
			book = (List<Booking>) query.getResultList();
		}
		catch(Exception exception) {
			throw new CustomException(FETCH_USER_BOOKING_DETAILS_EXCEPTION_MESSAGE + exception.getMessage());
		}
		return book;
	}
	
	public List<Booking> getMonthlyDashboardDetails(String email, String month) {
		System.out.println("user dashboard details fetched: " +email + " for month : " +month);
		Query query = entityManager.createQuery("from Booking as b WHERE (b.user_email =:email AND b.check_in_date >=:input)");
		query.setParameter("email",email);
		query.setParameter("input",DateUtility.todayDate(-365));
		List<Booking> booking = new ArrayList<>();
		List<Booking> filteredBooking = new ArrayList<>();
		try {
			booking = (List<Booking>) query.getResultList();
			for(Booking book : booking) {
				String bookingMonth = DateUtility.findMonth(DateUtility.getDate(book.getCheck_in_date()).getMonth());
				if(bookingMonth.equals(month.trim())) {
					filteredBooking.add(book);
				}
			}
		}
		catch(Exception exception) {
			throw new CustomException(FETCH_USER_BOOKING_DETAILS_EXCEPTION_MESSAGE + exception.getCause());
		}
		return filteredBooking;
	}
}
