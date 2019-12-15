package com.cmpe275.openhome.controller;

import java.net.URISyntaxException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.cmpe275.openhome.model.Card;
import com.cmpe275.openhome.model.User;
import com.cmpe275.openhome.repository.CardRepository;
import com.cmpe275.openhome.service.CardService;
import com.cmpe275.openhome.service.EmailService;
import com.cmpe275.openhome.service.UserService;
import com.cmpe275.openhome.utils.EmailUtility;


@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class AddCardDetailController {

	@Autowired
	CardRepository cardRepository;

	@Autowired
    private CardService cardService;
	
	@Autowired
    private UserService userService;
	
	@Autowired
	EmailService emailService;
	
	@PostMapping("/addcard")
    @ResponseBody
    public ResponseEntity<String> registration(@Valid @RequestBody Card card) throws URISyntaxException {
    	System.out.println("Body sent : " +card.getEmail());
    	User existingUser = userService.findByEmail(card.getEmail());
    	if(existingUser == null) {
    		System.out.println("User does not exist");
    		return new ResponseEntity<>("{\"status\" : \"No user found with sent email id.!!\"}", HttpStatus.BAD_REQUEST);
    	}
        cardService.saveCardDetails(card);
        String guestMessage = EmailUtility.createCardAdditionConfirmationMsg();
        emailService.sendEmail(card.getEmail(), guestMessage, " Payment method added successfully.!!");
        return new ResponseEntity<>("{\"status\" : \"User's card details saved successfully.!!\"}", HttpStatus.OK);
    }
	
    @ResponseBody
    @RequestMapping(method=RequestMethod.GET, value = "/fetchcard/{id}")
    public ResponseEntity<?> getCardDetails(@PathVariable Long id) {
    	System.out.println("User ID send as a parm : " +id);
    	List<Card> card = cardService.findCardDetails(id);
    	return ResponseEntity.ok(card);
    }
}
