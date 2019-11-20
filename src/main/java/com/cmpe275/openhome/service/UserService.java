package com.cmpe275.openhome.service;

import javax.servlet.http.HttpServletRequest;

import com.cmpe275.openhome.model.User;

public interface UserService {
    void register(User user);

    User findByEmail(String email);

	boolean verifyUserRegistration(Long ID);
	
	boolean loginUser(User user);
    
}