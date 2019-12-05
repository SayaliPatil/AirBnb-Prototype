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
	
	public boolean getBookingDetailsById(Property prop , String startDate , String endDate) {
		System.out.println("booking details fetched: " +prop.getId());
		Query query = entityManager.createQuery("from Booking as b WHERE (b.property_id =:id AND b.check_in_date >= :startDate AND b.check_out_date >= :endDate)");
		query.setParameter("id", prop.getId());
		query.setParameter("startDate", startDate);
		query.setParameter("endDate", endDate);
		
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
	
	public void updateBookedProperty(Booking book) throws ParseException, InterruptedException {
		Optional<Property> prop = propertyRepository.findById(book.getProperty_unique_id());
		Property updatedProperty = new Property();
		if(prop.isPresent()) {
			Long id = prop.get().getId();
			System.out.println("ID passed to booking controller : " +id +" Availabilty_start_date : " 
					+book.getAvailabilty_start_date() + "check in date : " +book.getCheck_in_date()  
					+ "availibility end date : " + book.getAvailabilty_end_date() + "check out date : " +book.getCheck_out_date());
			Property newProperty = new Property();
			updatedProperty.setAddress(prop.get().getAddress());
			updatedProperty.setDescription(prop.get().getDescription());
			updatedProperty.setHeadline(prop.get().getHeadline());
			updatedProperty.setHost_email(prop.get().getHost_email());
			updatedProperty.setImages(prop.get().getImages());
			updatedProperty.setMaxprice(prop.get().getMaxprice());
			updatedProperty.setMinprice(prop.get().getMinprice());
			updatedProperty.setPrice(prop.get().getPrice());
			updatedProperty.setProperty_id(prop.get().getProperty_id());
			updatedProperty.setProptype(prop.get().getProptype());
			updatedProperty.setSharingtype(prop.get().getSharingtype());
			updatedProperty.setSqft(prop.get().getSqft());
			updatedProperty.setWifi(prop.get().getWifi());
			if(book.getAvailabilty_start_date().equals(book.getCheck_in_date()) && book.getAvailabilty_end_date().equals(book.getCheck_out_date())) {
				updatedProperty.setId(id);
				updatedProperty.setBeds(prop.get().getBeds());
				updatedProperty.setBooked_flag(true);
				updatedProperty.setStartdate(prop.get().getStartdate());
				updatedProperty.setEnddate(prop.get().getEnddate());
				propertyRepository.save(updatedProperty);
			}
			else if(book.getAvailabilty_start_date().equals(book.getCheck_in_date()) && book.getAvailabilty_end_date().compareTo(book.getCheck_out_date()) > 0 ) {
				Date endDate = prop.get().getEnddate();
				updatedProperty.setId(id);
				updatedProperty.setBeds(prop.get().getBeds());
				updatedProperty.setBooked_flag(true);
				updatedProperty.setStartdate(prop.get().getStartdate());
				updatedProperty.setEnddate(new SimpleDateFormat("yyyy-MM-dd").parse(book.getCheck_out_date()));
				propertyRepository.save(updatedProperty);
				updatedProperty.setId(null);
				updatedProperty.setBeds(prop.get().getBeds());
				updatedProperty.setBooked_flag(false);
				updatedProperty.setStartdate(new SimpleDateFormat("yyyy-MM-dd").parse(book.getCheck_out_date()));
				updatedProperty.setEnddate(endDate);
				propertyRepository.save(updatedProperty);
			}
			else if(book.getCheck_out_date().equals(book.getAvailabilty_end_date()) && book.getCheck_in_date().compareTo(book.getAvailabilty_start_date()) > 0 ) {
				Date startDate = prop.get().getStartdate();
				updatedProperty.setId(id);
				updatedProperty.setBeds(prop.get().getBeds());
				updatedProperty.setBooked_flag(true);
				updatedProperty.setStartdate(new SimpleDateFormat("yyyy-MM-dd").parse(book.getCheck_in_date()));
				updatedProperty.setEnddate(prop.get().getEnddate());
				propertyRepository.save(updatedProperty);
				updatedProperty.setId(null);
				updatedProperty.setBeds(prop.get().getBeds());
				updatedProperty.setBooked_flag(false);
				updatedProperty.setStartdate(startDate);
				updatedProperty.setEnddate(new SimpleDateFormat("yyyy-MM-dd").parse(book.getCheck_in_date()));
				propertyRepository.save(updatedProperty);
			}
			else if(book.getCheck_in_date().compareTo(book.getAvailabilty_start_date()) > 0 && book.getAvailabilty_end_date().compareTo(book.getCheck_out_date()) > 0 ) {
				Date startDate = prop.get().getStartdate();
				Date endDate = prop.get().getEnddate();
				updatedProperty.setId(id);
				updatedProperty.setBeds(prop.get().getBeds());
				updatedProperty.setBooked_flag(true);
				updatedProperty.setStartdate(new SimpleDateFormat("yyyy-MM-dd").parse(book.getCheck_in_date()));
				updatedProperty.setEnddate(new SimpleDateFormat("yyyy-MM-dd").parse(book.getCheck_out_date()));
				propertyRepository.save(updatedProperty);
				updatedProperty.setId(null);
				updatedProperty.setBeds(prop.get().getBeds());
				updatedProperty.setBooked_flag(false);
				updatedProperty.setStartdate(startDate);
				updatedProperty.setEnddate(new SimpleDateFormat("yyyy-MM-dd").parse(book.getCheck_in_date()));
				propertyRepository.save(updatedProperty);
				newProperty.setAddress(prop.get().getAddress());
				newProperty.setDescription(prop.get().getDescription());
				newProperty.setHeadline(prop.get().getHeadline());
				newProperty.setHost_email(prop.get().getHost_email());
				newProperty.setImages(prop.get().getImages());
				newProperty.setMaxprice(prop.get().getMaxprice());
				newProperty.setMinprice(prop.get().getMinprice());
				newProperty.setPrice(prop.get().getPrice());
				newProperty.setProperty_id(prop.get().getProperty_id());
				newProperty.setProptype(prop.get().getProptype());
				newProperty.setSharingtype(prop.get().getSharingtype());
				newProperty.setSqft(prop.get().getSqft());
				newProperty.setWifi(prop.get().getWifi());
				newProperty.setId(null);
				newProperty.setBeds(prop.get().getBeds());
				newProperty.setBooked_flag(false);
				newProperty.setStartdate(new SimpleDateFormat("yyyy-MM-dd").parse(book.getCheck_out_date()));
				newProperty.setEnddate(endDate);
				propertyRepository.save(newProperty);
			}
		}
	}
}
