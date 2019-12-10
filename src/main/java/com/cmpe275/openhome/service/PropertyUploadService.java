package com.cmpe275.openhome.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Period;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmpe275.openhome.model.Account;
import com.cmpe275.openhome.model.Booking;
import com.cmpe275.openhome.model.Property;
import com.cmpe275.openhome.repository.PropertyRepository;
import com.cmpe275.openhome.utils.DateUtility;

@Service
public class PropertyUploadService {
	
	@Autowired
	private PropertyRepository propertyRepository;
	
	@Autowired BookingService bookingService;
	
	@Autowired
	private EntityManager entityManager;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private UserService userService;
	
	private static final String ERROR_IN_UPLOADING_PROPERTY = "Error in Uploading property";
	private static final String FETCH_PROPERTY_DETAILS_EXCEPTION_MESSAGE = "No property details found for the host";
	
	public Property uploadProperty(Property prop) {
		prop.setPrice(prop.getWeekdayprice());
		return propertyRepository.save(prop);
	}
	
	public Property updateProperty(Property data) throws ParseException {
		
		System.out.println("id:" + data.getId());
		System.out.println("startdate:" + data.getStartdate());
		System.out.println("enddate:" + data.getEnddate());
		System.out.println("weekday:" + data.getWeekdayprice());
		System.out.println("weekend:" + data.getWeekendprice());
		System.out.println("availabelDays:" + data.getAvailabledays());
		
		String[] days = data.getAvailabledays().toLowerCase().split(";");
		List<String> daysList = Arrays.asList(days);
	
		boolean sDate = false,eDate = false,weeks = false;
		
		Long id = data.getId();
		Optional<Property> previous = propertyRepository.findById(id);
		
		if(previous != null)
		{
			if(previous.get().getStartdate() != data.getStartdate())
					sDate = true;
			if(previous.get().getEnddate() != data.getEnddate())
					eDate = true;
		}
		
		List<Booking> bookings = bookingService.getBookingByProperty(id);
		System.out.println("Found bookings:"+ bookings.size());
		Set<Long> cancel = new HashSet<Long>();
		for(Booking booking : bookings)
		{
			System.out.println("Checking Booking:" + booking.getID());
			Date checkinDate = new SimpleDateFormat("yyyy-MM-dd").parse(booking.getCheck_in_date());
			Date checkoutDate = new SimpleDateFormat("yyyy-MM-dd").parse(booking.getCheck_out_date());
			SimpleDateFormat simpleDateformat = new SimpleDateFormat("EEEE");
			Date temp = checkinDate;
			
			int i=0;
			while(i<7 && temp.before(checkoutDate))
			{
				if(daysList.indexOf(simpleDateformat.format(temp).toLowerCase()) < 0)
					cancel.add(booking.getID());
				i++;
			}
			
			if(checkinDate.before(data.getStartdate()) || checkoutDate.after(data.getEnddate()))
			{
					cancel.add(booking.getID());
			}
		}
		
		System.out.println("Finishied checking all bookings");
		
		for(Long booking_id : cancel) {
			cancelbooking(booking_id);
		}
		
		previous.get().setAvailabledays(data.getAvailabledays());
		previous.get().setEnddate(data.getEnddate());
		previous.get().setStartdate(data.getStartdate());
		previous.get().setWeekdayprice(data.getWeekdayprice());
		previous.get().setWeekendprice(data.getWeekendprice());
		
		uploadProperty(previous.get());
		return previous.get();
	}
	
	public void cancelbooking(Long booking_id) throws ParseException {
		
		System.out.println("Cancel Request initiated for booking:" + booking_id);
		Account account = new Account();
		Optional<Booking> booking = bookingService.getBookingById(booking_id);
		
		account.setBookingID(booking_id);
		account.setPropertyID(booking.get().getPropertyId());
		account.setGuestID(userService.findByEmail(booking.get().getUser_email()).getID());
		account.setHostID(userService.findByEmail(booking.get().getHost_email()).getID());
		
		if(booking != null)
		{
			Date checkinDate = new SimpleDateFormat("yyyy-MM-dd").parse(booking.get().getCheck_in_date());
			Date checkoutDate = new SimpleDateFormat("yyyy-MM-dd").parse(booking.get().getCheck_out_date());
			Date today = Calendar.getInstance().getTime();
			if(today.getHours() >= 15)
				today.setDate(today.getDate() + 1);
			
			
			long diffInMillies = checkinDate.getTime() - today.getTime();
		    long checkinDiff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
		    System.out.println("checkinDiff : " +checkinDiff);
		    diffInMillies = checkoutDate.getTime() - today.getTime() ;
		    long checkoutDiff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
		    
		    double hostPenalty,guestCompensation;
		    System.out.println("diffInMillies" +diffInMillies);
		    System.out.println("checkoutDiff : " +checkoutDiff);
		    
		    if(booking.get().isUser_checked_in_flag())
		    {
		    	System.out.println("User checked in flaf : ");
		    	double perDayPrice = booking.get().getPrice() / booking.get().getTotal_nights();
		    	System.out.println("perDayPrice : "+perDayPrice);
		    	hostPenalty = (perDayPrice * checkoutDiff) * 0.15;
		    	
		    	guestCompensation = (perDayPrice * checkoutDiff) + hostPenalty; 
		    		
		    }
		    else
		    {
		    	if(checkinDiff < 7)
		    	{
		    		System.out.println("checkinDiff : " +checkinDiff);
		    		hostPenalty = booking.get().getPrice() * 0.15;
		    		guestCompensation = 0;
		    	}
		    	else
		    	{
		    		System.out.println("checkinDiff : " +checkinDiff);
		    		hostPenalty = 0;
		    		guestCompensation = 0;
		    	}
		    }
		    System.out.println("guestCompensation : " +guestCompensation);
		    System.out.println("hostPenalty : " +hostPenalty);
		    account.setGuestAccountBalance(guestCompensation);
	    	account.setHostAccountBalance(-hostPenalty);
	    	
	    	accountService.saveAccountDetails(account);
		  
		    booking.get().setBooking_cancelled(true);
		    bookingService.saveBookingDetails(booking.get());
		}
	}
	
	public Property deleteProperty(Property prop) throws ParseException {
		System.out.println("Delete property initiated for :" + prop.getId());
		Optional<Property> to_be_deleted = propertyRepository.findById(prop.getId());
		to_be_deleted.get().set_deleted(true);
		
		List<Booking> bookings = bookingService.getBookingByProperty(prop.getId());
		System.out.println("Found bookings:"+ bookings.size());
		Set<Long> cancel = new HashSet<Long>();
		
		System.out.println("Finishied checking all bookings");
			
			for(Long booking_id : cancel) {
				cancelbooking(booking_id);
			}
		return propertyRepository.save(to_be_deleted.get());
	}
}




