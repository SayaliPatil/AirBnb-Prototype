package com.cmpe275.openhome.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmpe275.openhome.exception.CustomException;
import com.cmpe275.openhome.model.Card;
import com.cmpe275.openhome.model.Property;
import com.cmpe275.openhome.model.User;
import com.cmpe275.openhome.repository.CardRepository;
import com.cmpe275.openhome.service.UserService;
@Service
public class CardService {
	
	@Autowired
    private CardRepository cardRepository;
	
	@Autowired
    private UserService userService;
	
	
	@Autowired
	private EntityManager entityManager;
	
	private static final String FETCH_CARD_EXCEPTION_MESSAGE = "No card details found for the user";
	
	public void saveCardDetails(Card card) {
		// TODO Auto-generated method stub
		System.out.println("card details : " +card.getAddress());
		cardRepository.save(card);
	}
	
	public List<Card> findCardDetails(Long id) {
		System.out.println("card details fetched: " +id);
		Query query = entityManager.createQuery("from Card as c WHERE (c.userID =:id)");
		query.setParameter("id", id);
		System.out.println("QUERY GENEARTED: " +query.getFirstResult());
		List<Card> card = null;
		try {
			card = (List<Card>)query.getResultList();
		}
		catch(Exception exception) {
			throw new CustomException(FETCH_CARD_EXCEPTION_MESSAGE);
		}
		return card;
	}

}
