package com.cmpe275.openhome.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmpe275.openhome.model.Card;
import com.cmpe275.openhome.repository.CardRepository;

@Service
public class CardService {
	
	@Autowired
    private CardRepository cardRepository;
	
	public void saveCardDetails(Card card) {
		// TODO Auto-generated method stub
		System.out.println("card details : " +card.getAddress());
		cardRepository.save(card);
		
	}

}
