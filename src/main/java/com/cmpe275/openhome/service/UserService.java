package com.cmpe275.openhome.service;

import javax.servlet.http.HttpServletRequest;

import com.cmpe275.openhome.model.User;

public interface UserService {
    void save(User user);

    User findByEmail(String email);
    
    void sendVerificationEmail(User user, HttpServletRequest request);
}