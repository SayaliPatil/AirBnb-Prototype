package com.cmpe275.openhome.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import com.cmpe275.openhome.model.Account;
import com.cmpe275.openhome.model.Booking;
import com.cmpe275.openhome.model.Property;
import com.cmpe275.openhome.repository.BookingRepository;
import com.cmpe275.openhome.utils.DateUtility;
import com.cmpe275.openhome.utils.EmailUtility;

@Component
public class CheckInOutService extends QuartzJobBean{
	@Autowired
    private BookingRepository bookingRepository;
	
	@Autowired
	private BookingService bookingService;
	
	@Autowired
	private PropertyService propertyService;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private UserService userService;
	
	private static final Logger logger = LoggerFactory.getLogger(CheckInOutService.class);
	
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		// TODO Auto-generated method stub
		logger.info("Executing Job with key {}", context.getJobDetail().getKey());
		logger.info("Executing Job with key {}", context.getJobDetail().getDescription());
		System.out.println("Start date in execute internal : " +context.getJobDetail().getJobDataMap().getString("startdate"));
		String today = context.getJobDetail().getJobDataMap().getString("startdate").split(" ")[0];
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = sdf.parse(today);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, -1);
		String yesterday = DateUtility.getStringDate(calendar.getTime());
		System.out.println("Yesterday value in execute internal : " +yesterday);
		updateBookingAfterCheckinout(context.getJobDetail().getDescription() , today , yesterday);
	}
	
	private void updateBookingAfterCheckinout(String jobType , String startDate , String endDate) {
		System.out.println("startDate : " +startDate);
		List<Booking> bookingList = bookingRepository.findAll();
		for(Booking booking : bookingList) {
			if(jobType.equals("check-out") && booking.isUser_checked_in_flag() && !booking.isBooking_cancelled() && startDate.equals(booking.getCheck_out_date())) {
				booking.setUser_check_out_date(startDate);
				booking.setUser_checked_out_flag(true);
				sendCheckinoutNotification(EmailUtility.createCheckOutConfirmationMsg() , EmailUtility.createCheckOutConfirmationMsgHost(),
						booking.getHost_email() , booking.getUser_email());
				bookingService.saveBookingDetails(booking);
			}
			else if(jobType.equals("check-in") && endDate.equals(booking.getCheck_in_date())) {
				if(!booking.isUser_checked_in_flag() && !booking.isBooking_cancelled()) {
					booking.setBooking_cancelled(true);
					booking.setNo_show(true);
					double perDayFine = (booking.getPrice() / booking.getTotal_nights()) * 0.3;
					if(booking.getTotal_nights() >= 2) {
						double totalFine = perDayFine * 2;
						booking.setAmount_paid(totalFine);
					}
					else {
						booking.setAmount_paid(perDayFine);
					}
					sendCancellationNotification(EmailUtility.createCancellationConfirmationMsg() , EmailUtility.createCancellationConfirmationMsgHost(),
								booking.getUser_email() , booking.getHost_email());
					bookingService.saveBookingDetails(booking);
					
				}
			}
			
		}
	}
	
	public void sendCheckinoutNotification(String guestMessage , String hostMessage, String guestEmail,  String hostEmail) {
		emailService.sendEmail(guestEmail, guestMessage, " Check-In/Check-out Confirmation with OpenHome.!!");
        emailService.sendEmail(hostEmail, hostMessage, " Check-In/Check-out Confirmation with OpenHome.!!");
	}
	
	public void sendCancellationNotification(String guestMessage , String hostMessage, String guestEmail,  String hostEmail) {
		emailService.sendEmail(guestEmail, guestMessage, " Booking Cancelled with OpenHome.!!");
        emailService.sendEmail(hostEmail, hostMessage, " Booked property got cancelled.!!");
	}
	
	public void updateAccountDetails(Booking booking, Long id , double guestAmount, double hostAmount) {
		System.out.println("Booking details sent to update account details: " +booking.getAmount_paid() + " " +id);
		Account account = accountService.findAccountDetails(id);
		if(account == null) {
			System.out.println("Account details fetched : " +account);
			account = new Account();
			account.setBookingID(id);
			account.setGuestID(userService.findByEmail(booking.getUser_email()).getID());
			account.setHostID(userService.findByEmail(booking.getHost_email()).getID());
			account.setPropertyID(booking.getPropertyId());
			System.out.println("Account details fetched after reading table: " +account);
		}
		account.setGuestAccountBalance(guestAmount);
		account.setHostAccountBalance(hostAmount);
		accountService.saveAccountDetails(account);
	}
}
