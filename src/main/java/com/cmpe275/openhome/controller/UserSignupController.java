package com.cmpe275.openhome.controller;

import java.net.URISyntaxException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.cmpe275.openhome.exception.CustomException;
import com.cmpe275.openhome.model.User;
import com.cmpe275.openhome.repository.UserRepository;
import com.cmpe275.openhome.service.EmailService;
import com.cmpe275.openhome.service.UserService;
import com.cmpe275.openhome.utils.EmailUtility;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class UserSignupController {

	@Autowired
	UserRepository userRepository;

	@Autowired
    private UserService userService;
	
	@Autowired
	EmailService emailService;
	
	private static final String USER_VERIFICATION_EXCEPTION_MESSAGE = "User account verification failed";
	private static final String USER_LOGIN_EXCEPTION_MESSAGE = "User login failed";
	
    @PostMapping("/signup")
    @ResponseBody
    public ResponseEntity<String> registration(@Valid @RequestBody User user) throws URISyntaxException {
    	System.out.println("Body sent : " +user.getEmail());
    	User existingUser = userService.findByEmail(user.getEmail());
    	if(existingUser != null) {
    		System.out.println("User exists");
    		return new ResponseEntity<>("{\"status\" : \"User with same email is already registered .!!\"}", HttpStatus.FOUND);
    	}
        userService.register(user);
        System.out.println("user is registered and verification code is sent");
        String message = EmailUtility.createVerificationMsg(user.getID());
        emailService.sendEmail(user.getEmail(), message, " User Profile Verification");
        return new ResponseEntity<>("{\"status\" : \"User Registered Successfully.!!\"}", HttpStatus.OK);
    }
    
    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<String> login(@Valid @RequestBody User user) throws URISyntaxException {
    	System.out.println("Body sent : " +user.getEmail());
    	User existingUser = userService.findByEmail(user.getEmail());
    	if(existingUser == null) {
    		System.out.println("User does not exist");
    		return new ResponseEntity<>("{\"status\" : \"Cannot login. User is not registered, first sign up..!!\"}", HttpStatus.NOT_FOUND);
    	}
    	boolean authorizedUser = userService.loginUser(user);
    	if(!authorizedUser) {
			System.out.println("User entered wrong email or password");
    		return new ResponseEntity<>("{\"status\" : \"User entered wrong email or password..!!\"}", HttpStatus.BAD_REQUEST);
		}
        System.out.println("User logged in successfully");
        return new ResponseEntity<>("{\"status\" : \"User Logged in Successfully.!!\"}", HttpStatus.OK);
    }
    
    @RequestMapping(value = "/verifyaccount", method = RequestMethod.GET)
	public ResponseEntity<?> verifyUserAccount(@RequestParam Long ID) {
    	System.out.println("Verification link clicked" + ID );
		try {
			boolean verificationStatus = userService.verifyUserRegistration(ID);
			System.out.println("verificationStatus: " +verificationStatus);
			if (verificationStatus) {
				return new ResponseEntity<>("{\"status\" : \"User is verified successfully!!\"}", HttpStatus.OK);
			} else if (!verificationStatus) {
				return new ResponseEntity<>("{\"status\" : \"User could not be verified because of bad request from user!!\"}", HttpStatus.MULTIPLE_CHOICES);
			}
		} catch (Exception exp) {
			System.out.println("Verification Exception:" + exp.getMessage());
			throw new CustomException(USER_VERIFICATION_EXCEPTION_MESSAGE); 
		}
		return new ResponseEntity<>("{\"status\" : \"User could not be verified because of server error!!\"}", HttpStatus.BAD_REQUEST);
	}
    
////    
//	@GetMapping("/user/{id}")
//	public Optional<User> getUserById(@PathVariable(value = "id") Long userId) {
//	    return userRepository.findById(userId);
//	}
}
