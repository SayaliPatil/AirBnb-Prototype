package com.cmpe275.openhome.service;

import java.util.Locale;
import java.util.Optional;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.cmpe275.openhome.exception.CustomException;
import com.cmpe275.openhome.model.User;
import com.cmpe275.openhome.repository.UserRepository;
import com.cmpe275.openhome.utils.EmailUtility;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	ApplicationEventPublisher eventPublisher;
	
	@Autowired
    private UserRepository userRepository;
	
	@Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	EmailService emailService;
	
	private static final String DUPLICATE_USER_EXCEPTION_MESSAGE = "User already registered with same email address";
	@Override
	public void register(User user) {
		// TODO Auto-generated method stub
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		System.out.println("Password: " +user.getPassword());
		userRepository.save(user);
		
	}
	
	public boolean loginUser(User user) {
		User activeUser = findByEmail(user.getEmail());
		return bCryptPasswordEncoder.matches(activeUser.getPassword(), user.getPassword());
	}
	@Override
	public User findByEmail(String email) {
		// TODO Auto-generated method stub
		User user = null;
		try {
			user = userRepository.findByEmail(email);
		}
		catch(Exception exception) {
			throw new CustomException(DUPLICATE_USER_EXCEPTION_MESSAGE);
		}
		return user;
	}
	
	public boolean verifyUserRegistration(Long ID) {
		System.out.println("ID sent from body : " + ID);
        Optional<User> user = userRepository.findById(ID);
        try{
            if(user != null && !user.get().isVerified()){
            	System.out.println("User found with id : " +user.get().getEmail());
                user.get().setVerified(true);
                System.out.println("User found with id is verified : " +user.get().isVerified());
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

}
