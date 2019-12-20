package com.cmpe275.openhome.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cmpe275.openhome.exception.CustomException;
import com.cmpe275.openhome.model.Account;
import com.cmpe275.openhome.model.Card;
import com.cmpe275.openhome.repository.AccountRepository;
import com.cmpe275.openhome.repository.CardRepository;

@Service
public class AccountService {
	
	@Autowired
    private AccountRepository accountRepository;

	@Autowired
	private EntityManager entityManager;
	
	private static final String FETCH_ACCOUNT_DETAILS_EXCEPTION_MESSAGE = "No account details found for the passed booking id";
	
	public void saveAccountDetails(Account account) {
		// TODO Auto-generated method stub
		System.out.println("Account details : " +account.getBookingID());
		accountRepository.save(account);
	}
	
	public Account findAccountDetails(Long id) {
		System.out.println("booking id passed: " +id);
		Query query = entityManager.createQuery("from Account as a WHERE (a.bookingID =:id)");
		query.setParameter("id", id);
		List<Account> accountDetails = null;
		try {
			accountDetails = (List<Account>) query.getResultList();
			System.out.println("accountDetails : " +accountDetails);
		}
		catch(Exception exception) {
			throw new CustomException(FETCH_ACCOUNT_DETAILS_EXCEPTION_MESSAGE);
		}
		
		return accountDetails.size() == 0 ? null : accountDetails.get(0);
	}

}
