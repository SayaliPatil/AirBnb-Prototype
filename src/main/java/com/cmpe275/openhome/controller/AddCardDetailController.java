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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cmpe275.openhome.model.Card;
import com.cmpe275.openhome.model.User;
import com.cmpe275.openhome.repository.CardRepository;
import com.cmpe275.openhome.repository.UserRepository;
import com.cmpe275.openhome.service.CardService;
import com.cmpe275.openhome.service.UserService;
import com.cmpe275.openhome.utils.EmailUtility;


@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class AddCardDetailController {

	@Autowired
	CardRepository cardRepository;

	@Autowired
    private CardService cardService;
	
	@Autowired
    private UserService userService;
	
	@PostMapping("/addcard")
    @ResponseBody
    public ResponseEntity<String> registration(@Valid @RequestBody Card card) throws URISyntaxException {
    	System.out.println("Body sent : " +card.getEmail());
    	User existingUser = userService.findByEmail(card.getEmail());
    	if(existingUser == null) {
    		System.out.println("User does not exist exist");
    		return new ResponseEntity<>("{\"status\" : \"No user found with sent email id.!!\"}", HttpStatus.BAD_REQUEST);
    	}
        cardService.saveCardDetails(card);
        return new ResponseEntity<>("{\"status\" : \"User's card details saved successfully.!!\"}", HttpStatus.OK);
    }

}
