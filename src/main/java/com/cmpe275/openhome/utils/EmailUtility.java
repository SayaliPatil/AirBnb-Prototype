package com.cmpe275.openhome.utils;

public class EmailUtility {
	
	public static final String URL = "http://localhost:3000";
	public static String URL_PREFIX = URL+"/verifyaccount/";
	public static final String VERIFICATION_SUCCESS_MESSAGE = "Congratulations.!! Account verified successfully";
	
	public static String createVerificationMsg(Long verificationcode) {
        String verificationMessage = "Thank you for registration with OpenHome !!!\nPlease verify your account"
                + " by clicking the url.\n"
                + "\nUrl : " + URL_PREFIX + verificationcode
                + "\n\nRegards\nTeam OpenHome";
        System.out.println("verificationMessage : " +verificationMessage);
        return verificationMessage;
    }
	
	public static String createBookingConfirmationMsg() {
        String bookingMessage = "Thank you for Booking with OpenHome !!!"
                + "\n\nRegards\nTeam OpenHome";
        System.out.println("bookingMessage : " +bookingMessage);
        return bookingMessage;
    }
	
	public static String createBookingConfirmationMsgHost() {
        String bookingMessage = "Your posting has been booked !!!"
                + "\n\nRegards\nTeam OpenHome";
        System.out.println("bookingMessage : " +bookingMessage);
        return bookingMessage;
    }
	
	public static String createCardAdditionConfirmationMsg() {
        String cardAdditionMessage = "Thank you for updating payment method with OpenHome !!!\n Card has been added successfully." 
                + "\n\nRegards\nTeam OpenHome";
        System.out.println("cardAdditionMessage : " +cardAdditionMessage);
        return cardAdditionMessage;
    }
	
}
