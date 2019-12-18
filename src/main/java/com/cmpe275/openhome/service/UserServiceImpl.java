package com.cmpe275.openhome.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.cmpe275.openhome.exception.CustomException;
import com.cmpe275.openhome.model.User;
import com.cmpe275.openhome.repository.UserRepository;
import com.cmpe275.openhome.utils.EmailUtility;
import com.cmpe275.openhome.utils.HashingUtility;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	ApplicationEventPublisher eventPublisher;
	
	@Autowired
    private UserRepository userRepository;
	
	@Autowired
	EmailService emailService;
	
	@Override
	public void register(User user) {
		// TODO Auto-generated method stub
		user.setPassword(HashingUtility.createHashedCode(user.getPassword()));
		System.out.println("Password: " +user.getPassword());
		userRepository.save(user);
		
	}
	
	public String loginUser(User user) {
		User activeUser = findByEmail(user.getEmail());
		if(!activeUser.isVerified()) {
			return "Not Verified";
		}
		else if(activeUser.getPassword().equals(HashingUtility.createHashedCode(user.getPassword()))) {
			return "Authenticated Verified User";
		}
		return  "Not authenticated";
	}
	@Override
	public User findByEmail(String email) {
		// TODO Auto-generated method stub
		User user = null;
		user = userRepository.findByEmail(email);
		return user;
	}
	
	public boolean verifyUserRegistration(Long ID) {
		System.out.println("ID sent from body : " + ID);
        Optional<User> user = userRepository.findById(ID);
        try{
            if(user != null && !user.get().isVerified()){
            	System.out.println("User found with id : " +user.get().getEmail());
                user.get().setVerified(true);
                userRepository.save(user.get());
                emailService.sendEmail(user.get().getEmail(), EmailUtility.VERIFICATION_SUCCESS_MESSAGE,
                        "Successful Account Verification");
                return true;
            }
            else if(user.get().isVerified()){
                return false;
            }
        }
        catch (Exception e){
            throw e;
        }
        return false;
    }
	
	@Override
	public User checkUserVerified(String email) {
		System.out.println("ID sent from body : " + email);
		User user = null;
		user = userRepository.findByEmail(email);
        try {
        	if(user != null && user.isVerified() && user.getOauth_flag()) {
        		return user;
        	}
        }
        catch (Exception e){
            throw e;
        }
		return user;
		
	}
}
