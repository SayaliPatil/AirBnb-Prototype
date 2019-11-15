package com.cmpe275.openhome.controller;

import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.cmpe275.openhome.model.User;
import com.cmpe275.openhome.repository.UserRepository;
import com.cmpe275.openhome.service.UserService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class UserSignupController {

	@Autowired
	UserRepository userRepository;

	@Autowired
    private UserService userService;
	
	@Autowired
	private HttpServletRequest request;
	
    @PostMapping("/signup")
    @ResponseBody
    public ResponseEntity<String> registration(@Valid @RequestBody User user) throws URISyntaxException {
    	System.out.println("Body sent : " +user.getEmail());
    	User existingUser = userService.findByEmail(user.getEmail());
    	if(existingUser != null) {
    		return new ResponseEntity<>("{\"status\" : \"User with same email is already registered .!!\"}", HttpStatus.EXPECTATION_FAILED);
    	}
        userService.save(user);
        userService.sendVerificationEmail(user, request);
        return new ResponseEntity<>("{\"status\" : \"User Registered Successfully.!!\"}", HttpStatus.OK);
    }
////    
//	@GetMapping("/user/{id}")
//	public Optional<User> getUserById(@PathVariable(value = "id") Long userId) {
//	    return userRepository.findById(userId);
//	}
}
