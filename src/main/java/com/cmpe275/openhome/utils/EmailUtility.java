package com.cmpe275.openhome.utils;

public class EmailUtility {
	
	public static final String URL = "http://localhost:3000";
	public static String URL_PREFIX = URL+"/verifyaccount/";
	public static final String VERIFICATION_SUCCESS_MESSAGE = "Congratulations.!! Account verified successfully";
	
	public static String createVerificationMsg(Long verificationcode) {
        String verificationMessage = "Thank you the registration with OpenHome !!!\nPlease verify your account"
                + " by clicking the url.\n"
                + "\nUrl : " + URL_PREFIX + verificationcode
                + "\n\nRegards\nTeam OpenHome";
        System.out.println("verificationMessage : " +verificationMessage);
        return verificationMessage;
    }
}
