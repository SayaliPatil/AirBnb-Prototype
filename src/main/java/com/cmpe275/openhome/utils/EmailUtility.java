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
	
	public static String createCheckInConfirmationMsg() {
		String checkinMessage = "Thank you for your check-in with OpenHome !!!" 
                + "\n\nRegards\nTeam OpenHome";
        System.out.println("checkinMessage : " +checkinMessage);
        return checkinMessage;
	}
	
	public static String createCheckInConfirmationMsgHost() {
		String checkinMessageHost = "Your booked property has been checked in by user !!!" 
                + "\n\nRegards\nTeam OpenHome";
        System.out.println("checkinMessageHost : " +checkinMessageHost);
        return checkinMessageHost;
	}
	
	public static String createCheckOutConfirmationMsg() {
		String checkoutMessage = "Thank you.!! You have been checked out successfully !!!" 
                + "\n\nRegards\nTeam OpenHome";
        System.out.println("checkinMessage : " +checkoutMessage);
        return checkoutMessage;
	}
	
	public static String createCheckOutConfirmationMsgHost() {
		String checkoutMessageHost = "Your booked property has been checked out by user !!!" 
                + "\n\nRegards\nTeam OpenHome";
        System.out.println("checkoutMessage : " +checkoutMessageHost);
        return checkoutMessageHost;
	}
	
	public static String createCancellationConfirmationMsg() {
		String cancellationMessage = "Thank you.!! Your booking has been cancelled with OpenHome !!!" 
                + "\n\nRegards\nTeam OpenHome";
        System.out.println("cancellationMessage : " +cancellationMessage);
        return cancellationMessage;
	}
	
	public static String createCancellationConfirmationMsgHost() {
		String cancellationMessageHost = "Your booked property has been cancelled !!!" 
                + "\n\nRegards\nTeam OpenHome";
        System.out.println("cancellationMessage : " +cancellationMessageHost);
        return cancellationMessageHost;
	}
	
	public static String createPropertyDeleteMessageHost() {
		String deleteMessageHost = "Your booked has been deleted Successfully" 
                + "\n\nRegards\nTeam OpenHome";
        System.out.println("deleteMessageHost : " +deleteMessageHost);
        return deleteMessageHost;
	}
	
	public static String createPropertyUpdateMessageHost() {
		String updateMessageHost = "Your booked has been updated Successfully" 
                + "\n\nRegards\nTeam OpenHome";
        System.out.println("deleteMessageHost : " +updateMessageHost);
        return updateMessageHost;
	}
}
